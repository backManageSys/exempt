package njurestaurant.njutakeout.springcontroller.order;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.Log.SystemControllerLog;
import njurestaurant.njutakeout.blservice.order.ChangeBlService;
import njurestaurant.njutakeout.data.dao.company.CompanyCardDao;
import njurestaurant.njutakeout.data.dao.order.CardChangeOrderDao;
import njurestaurant.njutakeout.data.dao.order.QRcodeChangeOrderDao;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayDataService;
import njurestaurant.njutakeout.dataservice.company.CompanyCardDataService;
import njurestaurant.njutakeout.dataservice.order.ChangeOrderDataService;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.app.Alipay;
import njurestaurant.njutakeout.entity.company.CompanyCard;
import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.order.CardChangeParameters;
import njurestaurant.njutakeout.parameters.order.TestParameters;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.order.OrderListResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
public class ChangeController {

    private final ChangeBlService changeBlService;
    private final ChangeOrderDataService changeOrderDataService;
    private final QRcodeChangeOrderDao qRcodeChangeOrderDao;
    private final CardChangeOrderDao cardChangeOrderDao;
    private final UserDataService userDataService;
    private final CompanyCardDao companyCardDao;
    private final CompanyCardDataService companyCardDataService;
    private final AlipayDataService alipayDataService;
    @Autowired
    public ChangeController(ChangeBlService changeBlService, ChangeOrderDataService changeOrderDataService, QRcodeChangeOrderDao qRcodeChangeOrderDao, CardChangeOrderDao cardChangeOrderDao, UserDataService userDataService, CompanyCardDao companyCardDao, CompanyCardDataService companyCardDataService, AlipayDataService alipayDataService) {
        this.changeBlService = changeBlService;
        this.changeOrderDataService = changeOrderDataService;
        this.qRcodeChangeOrderDao = qRcodeChangeOrderDao;
        this.cardChangeOrderDao = cardChangeOrderDao;
        this.userDataService = userDataService;
        this.companyCardDao = companyCardDao;
        this.companyCardDataService = companyCardDataService;
        this.alipayDataService = alipayDataService;
    }

    /*@ApiOperation(value = "查看内部码账变订单", notes = "查看内部码账变信息")
    @RequestMapping(value = "internalaccountchange/qrcode", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> ShowQRcodeChangeOrder(@PageableDefault(value = 3000,sort = { "operateTime" }, direction = Sort.Direction.DESC ) Pageable pageable, @RequestBody QRcodeChangeOrder qRcodeChangeOrder) {
        return new ResponseEntity<>(new JSONResponse(200, changeOrderDataService.findAllQrCodeChangeOrder(pageable,qRcodeChangeOrder)), HttpStatus.OK);
    }*/

    @ApiOperation(value = "查看内部码账变订单", notes = "查看内部码账变信息")
    @RequestMapping(value = "internalaccountchange/qrcode", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> ShowQRcodeChangeOrder(@RequestParam("condition") String  condition,@RequestParam("page") Integer page,@RequestParam("size") Integer size) {

        return new ResponseEntity<>(new JSONResponse(200, changeOrderDataService.findAllQrCodeChangeOrder(condition,page,size)), HttpStatus.OK);
    }

    @SystemControllerLog(descrption = "添加内部卡账变订单",actionType = "1")
    @ApiOperation(value = "添加内部卡订单", notes = "添加内部卡订单")
    @RequestMapping(value = "internalaccountchange/card/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> AddCardChangeOrder(@RequestBody CardChangeParameters CardChangeParameters) {
        try {
            changeBlService.addCardChangeOrder(CardChangeParameters);
            return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("发出转账成功，等待审核。")), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "该用户无法进行该转账操作。")), HttpStatus.OK);
        } catch (WrongInputException e) {
            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "银行卡余额不足")), HttpStatus.OK);
        } catch (PersonalCardDoesNotExistException e) {
            return new ResponseEntity<>(new JSONResponse(10411, new WrongResponse(10411, "个人银行卡不存在。")), HttpStatus.OK);
        } catch (CompanyCardDoesNotExistException e) {
            return new ResponseEntity<>(new JSONResponse(10412, new WrongResponse(10412, "公司银行卡不存在。")), HttpStatus.OK);
        }
    }


    @ApiOperation(value = "查看内部卡账变订单", notes = "查看内部卡账变订单")
    @RequestMapping(value = "internalaccountchange/ShowCardOrder", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    public ResponseEntity<Response> GetCardChangeOrder(HttpServletRequest request, @RequestParam("condition") String condition, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {


        String Token=request.getHeader("X-Token");
        String [] line=Token.split(",");
        User user=userDataService.getUserByUsername(line[1]);
        if (user == null) {
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "该用户无法查看内部卡账变订单。")), HttpStatus.OK);
        }else if (user.getRole() == 1) {
            return new ResponseEntity<>(new JSONResponse(200, changeOrderDataService.findAllCardChangeOrder(null,condition,page,size)), HttpStatus.OK);
        } else if (user.getRole() == 4) {
            return new ResponseEntity<>(new JSONResponse(200, changeOrderDataService.findAllCardChangeOrder(user.getUsername(),condition,page,size)), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "该用户无法查看内部卡账变订单。")), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "修改内部卡账变订单",actionType = "3")
    @ApiOperation(value = "修改内部卡账变订单", notes = "修改内部卡账变订单")
    @RequestMapping(value = "internalaccountchange/card/update/{id}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> UpdateCardChangeOrder(@PathVariable("id") int id , @RequestParam("uid") int uid) {
        CardChangeOrder cardChangeOrder = cardChangeOrderDao.findById(id);
        if (cardChangeOrder.getState()!=WithdrewState.WAITING)
            return new ResponseEntity<>(new JSONResponse(200, "不能修改当前状态的订单"), HttpStatus.OK);
        cardChangeOrder.setMoney_in(cardChangeOrder.getMoney_out());
        cardChangeOrder.setState(WithdrewState.SUCCESS);
        cardChangeOrder.setFinalOperateTime(new Date());
        cardChangeOrder.setCardBalanceIn(cardChangeOrder.getCardBalanceIn()+cardChangeOrder.getMoney_out());
        changeOrderDataService.saveCardChangeOrder(cardChangeOrder);
        //更新供码归集卡或总公司银行卡余额
        CompanyCard companyCard = companyCardDao.findCompanyCardByCardNumber(cardChangeOrder.getCardNumber_in());
        companyCard.setBalance(companyCard.getBalance() + cardChangeOrder.getMoney_in());
        companyCardDataService.saveCompanyCard(companyCard);
        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("更新内部卡账变订单以及公司银行卡余额成功")), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "撤销内部卡账变订单",actionType = "3")
    @ApiOperation(value = "撤销内部卡账变订单", notes = "撤销内部卡账变订单")
    @RequestMapping(value = "internalaccountchange/card/revoke/{id}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> RevokeCardChangeOrder(@PathVariable("id") int id,@RequestParam("reason") String reason) {
        CardChangeOrder cardChangeOrder = cardChangeOrderDao.findById(id);
        if (cardChangeOrder.getState()!=WithdrewState.WAITING)
            return new ResponseEntity<>(new JSONResponse(200, "不能撤销当前状态的订单"), HttpStatus.OK);
        if (StringUtils.isBlank(reason))
            return new ResponseEntity<>(new JSONResponse(200, "撤销原因不能为空"), HttpStatus.OK);
        double money = cardChangeOrder.getMoney_out();
        cardChangeOrder.setState(WithdrewState.FAILED);
        cardChangeOrder.setReason(reason);
        cardChangeOrder.setFinalOperateTime(new Date());
        changeOrderDataService.saveCardChangeOrder(cardChangeOrder);
        //更新供码归集卡余额或供码个人卡余额
        if (cardChangeOrder.getType().equals("入款")){
            Alipay alipay = alipayDataService.findByCardNumber(cardChangeOrder.getCardNumber_out());
            alipay.setCardBalance(alipay.getCardBalance() + money);
            alipayDataService.saveAlipay(alipay);
        }else if (cardChangeOrder.getType().equals("储备")){
            CompanyCard companyCard = companyCardDao.findCompanyCardByCardNumber(cardChangeOrder.getCardNumber_out());
            companyCard.setBalance(companyCard.getBalance() + money);
            companyCardDataService.saveCompanyCard(companyCard);
        }
        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("撤销内部卡账变订单以及修改公司银行卡余额成功")), HttpStatus.OK);
    }

}
