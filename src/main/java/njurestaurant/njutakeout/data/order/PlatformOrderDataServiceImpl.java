package njurestaurant.njutakeout.data.order;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import njurestaurant.njutakeout.data.dao.order.PlatformOrderDao;
import njurestaurant.njutakeout.dataservice.account.AgentDataService;
import njurestaurant.njutakeout.dataservice.account.MerchantDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayOrderDataService;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.AliToken;
import njurestaurant.njutakeout.entity.CollectionOrder;
import njurestaurant.njutakeout.entity.account.Agent;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.order.AlipayOrder;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.exception.AliUserErrorException;
import njurestaurant.njutakeout.exception.OrderWrongInputException;
import njurestaurant.njutakeout.publicdatas.account.AgentDailyFlow;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.util.FormatDateTime;
import njurestaurant.njutakeout.util.StringParseUtil;
import njurestaurant.njutakeout.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

import static njurestaurant.njutakeout.config.websocket.WebSocketHandler.GetThreeBitsPoint;
import static njurestaurant.njutakeout.publicdatas.order.OrderState.PAID;


@Service
public class PlatformOrderDataServiceImpl implements PlatformOrderDataService {
    private final PlatformOrderDao platformOrderDao;
    private final AlipayOrderDataService alipayOrderDataService;
    private final UserDataService userDataService;
    private final MerchantDataService merchantDataService;
    private final AgentDataService agentDataService;

    @Autowired
    public PlatformOrderDataServiceImpl(PlatformOrderDao platformOrderDao, AlipayOrderDataService alipayOrderDataService, UserDataService userDataService, MerchantDataService merchantDataService, AgentDataService agentDataService) {
        this.platformOrderDao = platformOrderDao;
        this.alipayOrderDataService = alipayOrderDataService;
        this.userDataService = userDataService;
        this.merchantDataService = merchantDataService;
        this.agentDataService = agentDataService;
    }

    @Override
    public PlatformOrder findById(int id) {
        Optional<PlatformOrder> platformOrderOption = platformOrderDao.findById(id);
        if (platformOrderOption.isPresent()) return platformOrderOption.get();
        else return null;
    }

    @Override
    public List<PlatformOrder> findByUid(int uid) {
        return platformOrderDao.findPlatformOrderByUid(uid);
    }

    @Override
    public PlatformOrder savePlatformOrder(PlatformOrder platformOrder) {
        return platformOrderDao.save(platformOrder);
    }

    @Override
    public void deleteById(int id) {
        platformOrderDao.deleteById(id);
    }

    @Override
    public List<PlatformOrder> findByState(OrderState orderState) {
        return platformOrderDao.findPlatformsByState(orderState);
    }

    @Override
    public Page<PlatformOrder> findAll(String condition,String uid,Pageable pageable) {
        return  platformOrderDao.findAll(condition,uid,pageable);
    }

    @Override
    public List<PlatformOrder> findByUidAndState(int uid, OrderState orderState) {
        return platformOrderDao.findPlatformOrderByUidAndState(uid, orderState);
    }

    @Override
    public PlatformOrder findByNumber(String number) {
        return platformOrderDao.findPlatformOrderByNumber(number);
    }

    @Override
    public PlatformOrder findByImeiAndState(String imei, OrderState orderState) {
        return platformOrderDao.findPlatformOrdersByImeiAndState(imei, orderState);
    }

    @Override
    public void savePlatformOrders(List<PlatformOrder> platformOrders) {
        platformOrderDao.saveAll(platformOrders);
    }

    @Override
    public List<PlatformOrder> findPlatformByDate(Date startDate, Date endDate) {
        return platformOrderDao.findAll(dateBetween(startDate, endDate));
    }

    @Override
    public List<PlatformOrder> findByImeiAndPayTypeId(String imei, int payTypeId) {
        return platformOrderDao.findPlatformOrderByImeiAndPayTypeId(imei, payTypeId);
    }

    @Override
    public PlatformOrder findByImeiAndStateAndPayTypeIdAndMoney(String imei, OrderState orderState, int payTypeId, double money) {
        return platformOrderDao.findPlatformOrderByImeiAndStateAndPayTypeIdAndMoney(imei, orderState, payTypeId, money);
    }

    /**
     * 向安卓发起请求  钉子 2019年3月19日14:37:18
     * @return
     */
    @Override
    public String getOrderInfoFromAuthority(String auth_token,String state) throws AliUserErrorException{
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "2018110561986668" ,"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDP/BiT3XC9/WDbwNhAgWWNR0vOMpZ7eecPaT+gkfqVYsPy5q/lGqweuEszRqLTlVmg2h9baeWXgSdiZjJ0G2ZJzI6n0dAKDENqwov42+XJ6HcHvDozJjskf9LU+NGCyx74fc8wABYay9Z4nujv+Kil8mbPIsGOButelfIDj8DZNsYno/JKtO/wsDMUf9jdPdrgL3Fdq93nhTnhHdZTr7PvG8rXZ23N5lMnzSW3tIh0WhzsYwa4dSTZVR0N2qFmjspuKxKumRmYPuzmkJuube9HqHFZGogw6ibJVV2ZGT/t7yYpjtD7L8KrG624cwBQ8R0kjpGuy2l2pdZThqrRHVQxAgMBAAECggEAewt1ruTu8jeYuemZBnCHF9H6Mv8Sr/IqlJ97mQ9D5GUN8AIyRaRF99aoBqnfYrWU1Oi2bGp1/e/W0QwNBUvAdCCktPwXzBn9OIfoL05I3+67nMPq2p/flXRZ0s1xhaONqw0jKg6jBS9arvm0bWP6wlJqKFkzkUbesJKdHyZIvTfsX3eKQHZMkpSwt/mB7/jeFr/oPMjtwnUMhoHAk4z4YT8g2AfYp3kAs5t3zv3LOLE5bd6WxViWZLBjf2pxHGFZDx/Vz3SYrmttEeBdpxkFhSj+SVPHEHxwzesyWL50DCJbwHEcAcyXq3f3llBTdss7xyAs1/gehgdUVR8hCoLpAQKBgQD8Ot4rCf1EkDhAQu7sj1aN1WDq5a7IUoWsFec85JBkZMaedA6231IuG0yPUgEfMv/vQHaPjOCz4cU0tkL1O9kcpJZybOkEriQVWUOlGPHoab1eSVsP+5HsjzwEcG96/PolF/acAdpEiThdJ3rvoALWAKudD3Jf0UYiWaWj3y9ZKQKBgQDTF+2seOO+BF2fHk0dJ5Aa8YS2t3j162VZDel1nMbBdkOEZPq9igWT+GBKefKEtMO6eT3A9mSIpiwMDQWeBLb2ptgtvnaIGTiVfWsTHHPV3EOiMVryiqDgBLkGuyidfijm9NPW7qj3s3C5dHjpbI/Q22OhFYei5MzlagXbHmEbyQKBgB5O6JFdYuyRM+Es6c7NLoZehIXVUZpKqDtUanPbtyoJHgC+S2JYFWTq7Nkv7miUVUKniVeYgwu6blr2J7eWGHGjqVxYj+t7DWTvU/yLsc3Onu2sfN+/x/oYAAs8g3jQ2zIH3h9C7bpcjsRK0S/oF08lx3lY+NJgsD/Ca+/0Cpl5AoGASofBR8rrBfT+pSaKrYeBbrHlXKnM0G8LOnUZ/4eZ+UZrMjyA9xjbPVJNlR4XTVmRXa5e6llUR5b8KEkUuaUKgG2KGCSOLyFQhB9jBb1e/JwnWQW6CbZ+dHnWZhUAkxKvGFRK0zEygTpey2vfIPNOl7xOIdq7J/1Ghp13cdT5DlkCgYEAlFqGxGgEiRXR8lNTFBMgxwv22ujsJEsm7nmTNZJ0jIZc5KR9AJz/gboTSot9ka1eV0JFasbHJeN27dqX1bUU86DGNVzQz/mPtkIiXu0mmlFxF7IBqrMat1Hda2Fn/nprElWOGO78ZnxX0azETKB84tLEfx5k8FxlLh0M8V4yyo8=", "json", "GBK", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqHTuDzJ4xLsw53agBNHInfX25yPji8RwNUKN2HiGiKgl7pl9h0m4CMYUVNkQLoN3vxHT8+ypVW1FLS7JesL5qL7uIHRWwFtqkgBTFiW8aTDqgWCta9y2JtHONnt802dS6qUJgx4uqZdFkqwfZgjUWYIWhPMNun15CD3KvHXz4zf39MneWQmplUD04qjxtUzhEquBOl4ZNcyyLYEG/8Kq1EtK8AZMXhCxhHuVLU6G8pAYy5982idRkAcuiwv5hxGpUxNgNkZn3qB3G0c5LHbbsphd5Zb+bNznWhHz6+rJkBWF2B0KYFoGnXTPinbKE7tIXRr8alwA/1xrQfYlIe6fnQIDAQAB", "RSA2");
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(auth_token);
        request.setGrantType("authorization_code");
        String aliUserId="";
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            aliUserId=oauthTokenResponse.getUserId();

            if (StringUtils.isBlank(aliUserId)){
                throw new AliUserErrorException();
            }
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }
        String authorizeUrl="http://xuanlv2.natapp1.cc/api/ask_device";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String[] strings=state.split("-");
        params.add("cmd", "personal");
        params.add("type", "alipay");
        params.add("touserid", aliUserId);
        params.add("money", strings[0]);
        params.add("memo", strings[1]);


        //发送Post数据并返回数据
        String string = Utils.sendPostRequest(authorizeUrl, params);
        return string;
    }

    @Override
    public String getInfoByAndroid(CollectionOrder collectionOrder) throws  OrderWrongInputException{
        if (collectionOrder.getCmd().equals("order")&&collectionOrder.getOrdertype().equals("personalorder")) {
            PlatformOrder platformOrder = platformOrderDao.findPlatformOrderByNumber(collectionOrder.getMemo());
            if (platformOrder == null) {
                throw new OrderWrongInputException(new WrongResponse(9999, "订单不存在"));
            } else {
                Date date = FormatDateTime.TenTimestampToDate(StringParseUtil.StringToInt(collectionOrder.getTime()));
                if (platformOrder.getState() == OrderState.EXPIRED || platformOrder.getState() == OrderState.WAITING_FOR_PAYING) {
                    AlipayOrder alipayOrder = new AlipayOrder(platformOrder.getImei(), collectionOrder.getMemo(),
                            Double.parseDouble(collectionOrder.getMoney()),collectionOrder.getMemo(), date);
                    alipayOrderDataService.saveAlipayOrder(alipayOrder);
                } else if (platformOrder.getState() == PAID) {
                    throw new OrderWrongInputException(new WrongResponse(9997, "不允许修改已支付订单"));
                }
                platformOrder.setMoney(Double.parseDouble(collectionOrder.getMoney()));
                platformOrder.setPayMoney(Double.parseDouble(collectionOrder.getMoney()));
                platformOrder.setPayTime(date);
                platformOrder.setState(PAID);
                platformOrderDao.save(platformOrder);
                //   platformOrderDataService.savePlatformOrder(platformOrder);
                User user = userDataService.getUserById(platformOrder.getUid());
                if (user != null) {
                    Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                    User suser = userDataService.getUserById(merchant.getApplyId());
                    if (merchant != null) {

                        merchant.setBalance(GetThreeBitsPoint(merchant.getBalance() + Double.parseDouble(collectionOrder.getRealMoney()) * (1 - platformOrder.getMerchantRate() / 100)));
                        merchantDataService.saveMerchant(merchant);
                    }
                    // 操作上级,商户的代理商
                    if (suser != null) {
                        if (suser.getRole() == 2) {
                            Agent agent = agentDataService.findAgentById(suser.getTableId());
                            agent.setBalance(GetThreeBitsPoint(agent.getBalance() + Double.parseDouble(collectionOrder.getRealMoney()) * (platformOrder.getMerchantRate() - platformOrder.getAgentRate()) / 100));
                            agentDataService.saveAgent(agent);
                            if (AgentDailyFlow.date == null)
                                AgentDailyFlow.date = new Date();
                            if (!DateUtils.isSameDay(AgentDailyFlow.date, new Date())) {
                                AgentDailyFlow.commission.clear();
                                AgentDailyFlow.flow.clear();
                                AgentDailyFlow.date = new Date();
                            }
                            // 计算代理的每日流量
                            if (AgentDailyFlow.flow.containsKey(agent.getId())) {
                                AgentDailyFlow.flow.put(agent.getId(), GetThreeBitsPoint(AgentDailyFlow.flow.get(agent.getId()) + Double.parseDouble(collectionOrder.getRealMoney()) * (1 - platformOrder.getMerchantRate() / 100)));
                            } else {
                                AgentDailyFlow.flow.put(agent.getId(), GetThreeBitsPoint(Double.parseDouble(collectionOrder.getRealMoney()) * (1 - platformOrder.getMerchantRate() / 100)));
                            }
                            // 计算代理的每日佣金
                            if (AgentDailyFlow.commission.containsKey(agent.getId())) {
                                AgentDailyFlow.commission.put(agent.getId(), GetThreeBitsPoint(AgentDailyFlow.commission.get(agent.getId()) + Double.parseDouble(collectionOrder.getRealMoney()) * (platformOrder.getMerchantRate() - platformOrder.getAgentRate()) / 100));
                            } else {
                                AgentDailyFlow.commission.put(agent.getId(), GetThreeBitsPoint(Double.parseDouble(collectionOrder.getRealMoney()) * (platformOrder.getMerchantRate() - platformOrder.getAgentRate()) / 100));
                            }
                        }
                    }
                }

            }
        }
            return null;
    }


    @Override
    public List<PlatformOrder> findByImeiAndStateAndPayTypeId(String imei, OrderState orderState, int payTypeId) {
        return platformOrderDao.findPlatformOrderByImeiAndStateAndPayTypeId(imei, orderState, payTypeId);
    }

    private Specification<PlatformOrder> dateBetween(Date startDate, Date endDate) {
        return new Specification<PlatformOrder>() {
            @Override
            public Predicate toPredicate(Root<PlatformOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("time"), startDate, endDate);
            }
        };
    }

/*    public Page<PlatformOrder> condition(Pageable pageable, PlatformOrder platformOrder) {
        return platformOrderDao.findAll(new Specification<PlatformOrder>() {
            @Override
            public Predicate toPredicate(Root<PlatformOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(platformOrder.getImei())) {
                    list.add(cb.equal(root.get("imei").as(String.class), platformOrder.getImei()));
                }
                if (platformOrder.getOrderState() != null) {
                    int i=0;
                    for(OrderState orderState: OrderState.values())
                        if (String.valueOf(orderState).equals(platformOrder.getOrderState())) {
                            i=1;
                            break;
                        }
                    if (i == 1) {
                        OrderState orderState = OrderState.valueOf(platformOrder.getOrderState());
                        list.add(cb.equal(root.get("state").as(OrderState.class), orderState));
                    }else {
                        //查一个不存在的值，目的是返回空查询
                        list.add(cb.equal(root.get("state").as(OrderState.class), OrderState.ARRIVED));
                        query.where(cb.and(list.toArray(new Predicate[list.size()])));
                        return query.getRestriction();
                    }
                }
                if (platformOrder.getPayTypeId() != 0) {
                    list.add(cb.equal(root.get("payTypeId").as(Integer.class), platformOrder.getPayTypeId()));
                }
                if (!StringUtils.isEmpty(platformOrder.getRechargeId())) {
                    list.add(cb.equal(root.get("rechargeId").as(String.class), platformOrder.getRechargeId()));
                }
                if (!StringUtils.isEmpty(platformOrder.getNumber())) {
                    list.add(cb.equal(root.get("number").as(String.class), platformOrder.getNumber()));
                }
                if (!StringUtils.isEmpty(platformOrder.getMerchantName())) {
                    list.add(cb.equal(root.get("merchantName").as(String.class), platformOrder.getMerchantName()));
                }
                if (!StringUtils.isEmpty(platformOrder.getType())) {
                    list.add(cb.equal(root.get("type").as(String.class), platformOrder.getType()));
                }
                if (platformOrder.getStartDate() != null) {
                    list.add(cb.greaterThan(root.get("time").as(Date.class), platformOrder.getStartDate()));
                }
                if (platformOrder.getEndDate() != null) {
                    list.add(cb.lessThan(root.get("time").as(Date.class), platformOrder.getEndDate()));
                }
                query.where(cb.and(list.toArray(new Predicate[list.size()])));
                return query.getRestriction();
            }
        }, pageable);
    }
*/
}
