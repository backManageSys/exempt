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
import njurestaurant.njutakeout.dataservice.company.CompanyCardDataService;
import njurestaurant.njutakeout.dataservice.order.ChangeOrderDataService;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.company.CompanyCard;
import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.order.CardChangeParameters;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChangeController {

    private final ChangeBlService changeBlService;
    private final ChangeOrderDataService changeOrderDataService;
    private final QRcodeChangeOrderDao qRcodeChangeOrderDao;
    private final CardChangeOrderDao cardChangeOrderDao;
    private final UserDataService userDataService;
    private final CompanyCardDao companyCardDao;
    private final CompanyCardDataService companyCardDataService;

    @Autowired
    public ChangeController(ChangeBlService changeBlService, ChangeOrderDataService changeOrderDataService, QRcodeChangeOrderDao qRcodeChangeOrderDao, CardChangeOrderDao cardChangeOrderDao, UserDataService userDataService, CompanyCardDao companyCardDao, CompanyCardDataService companyCardDataService) {
        this.changeBlService = changeBlService;
        this.changeOrderDataService = changeOrderDataService;
        this.qRcodeChangeOrderDao = qRcodeChangeOrderDao;
        this.cardChangeOrderDao = cardChangeOrderDao;
        this.userDataService = userDataService;
        this.companyCardDao = companyCardDao;
        this.companyCardDataService = companyCardDataService;
    }

    @ApiOperation(value = "查看内部码账变订单", notes = "查看内部码账变信息")
    @RequestMapping(value = "internalaccountchange/qrcode", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> ShowQRcodeChangeOrder() {
        return new ResponseEntity<>(new JSONResponse(200, qRcodeChangeOrderDao.findAll()), HttpStatus.OK);
    }

    @ApiOperation(value = "查看内部卡账变订单", notes = "查看内部卡账变信息")
    @RequestMapping(value = "internalaccountchange/C2Pcard", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> ShowCardChangeOrder() {
        return new ResponseEntity<>(new JSONResponse(200, cardChangeOrderDao.findAll()), HttpStatus.OK);
    }
//    @ApiOperation(value = "显示内部码账变订单", notes = "显示内部码账变信息")
//    @RequestMapping(value = "internalaccountchange/qrcode", method = RequestMethod.PUT)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> UpdateQRcodeChangeOrder( double real_money, double card_balance) {
//
////        QRcodeChangeOrder qRcodeChangeOrder = qRcodeChangeOrderDao.findById(id);
////        qRcodeChangeOrder.setRealMoney(real_money);
////        qRcodeChangeOrder.setCardBalance(card_balance);
////        qRcodeChangeOrder.setState(WithdrewState.SUCCESS);
////        changeOrderDataService.saveQRcodeChangeOrder(qRcodeChangeOrder);
//        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("更新内部码账变订单成功")), HttpStatus.OK);
//    }
    @SystemControllerLog(descrption = "发起内部卡转账申请(个人卡转入公司卡)",actionType = "1")
    @ApiOperation(value = "发起内部卡转账申请(个人卡转入公司卡)", notes = "发起内部卡转账申请(个人卡转入公司卡)")
    @RequestMapping(value = "internalaccountchange/P2Ccard", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> P2CCardChangeOrder(@RequestBody CardChangeParameters CardChangeParameters) {
        try {
            changeBlService.addP2CCardChangeOrder(CardChangeParameters);
            return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("发出转账成功，等待审核。")), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "该用户无法进行该转账操作。")), HttpStatus.OK);
        } catch (WrongInputException e) {
            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "转入金额大于该用户现有的余额。")), HttpStatus.OK);
        } catch (PersonalCardDoesNotExistException e) {
            return new ResponseEntity<>(new JSONResponse(10411, new WrongResponse(10411, "个人银行卡不存在。")), HttpStatus.OK);
        } catch (CompanyCardDoesNotExistException e) {
            return new ResponseEntity<>(new JSONResponse(10412, new WrongResponse(10412, "公司银行卡不存在。")), HttpStatus.OK);
        }
    }



//    @ApiOperation(value = "内部卡账变(公司卡转入个人卡)", notes = "发起内部卡转账申请(公司卡转入个人卡)")
//    @RequestMapping(value = "internalaccountchange/C2Pcard", method = RequestMethod.POST)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> C2PCardChangeOrder(@RequestBody CardChangeParameters CardChangeParameters) {
//        try {
//            changeBlService.addC2PCardChangeOrder(CardChangeParameters);
//            return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("发出转账成功，等待审核。")), HttpStatus.OK);
//        } catch (WrongIdException e) {
//            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "该用户无法进行该转账操作。")), HttpStatus.OK);
//        } catch (WrongInputException e) {
//            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "转出金额大于公司现有的余额。")), HttpStatus.OK);
//        } catch (PersonalCardDoesNotExistException e) {
//            return new ResponseEntity<>(new JSONResponse(10411, new WrongResponse(10411, "个人银行卡不存在。")), HttpStatus.OK);
//        } catch (CompanyCardDoesNotExistException e) {
//            return new ResponseEntity<>(new JSONResponse(10412, new WrongResponse(10412, "公司银行卡不存在。")), HttpStatus.OK);
//        }
//    }

//    @ApiOperation(value = "查看某个用户发起的内部码账变订单", notes = "查看某个用户发起的内部码账变订单")
//    @RequestMapping(value = "internalaccountchange/qrcode/history/{id}", method = RequestMethod.POST)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> GetQRcodeChangeOrderById(@PathVariable("id") int id) {
//        try {
//            return new ResponseEntity<>(new JSONResponse(200, changeBlService.getQRcodeChangeHistoryById(id)), HttpStatus.OK);
//        } catch (WrongIdException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new JSONResponse(10160, e.getResponse()), HttpStatus.OK);
//        }
//    }


    @ApiOperation(value = "查看内部卡账变订单", notes = "查看内部卡账变订单")
    @RequestMapping(value = "internalaccountchange/ShowCardOrder", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> GetCardChangeOrder(@RequestParam int id) {
        User user = userDataService.getUserById(id);
        if (user == null)
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "该用户无法查看内部码账变订单。")), HttpStatus.OK);
        if (user.getRole() == 1) {
            return new ResponseEntity<>(new JSONResponse(200, cardChangeOrderDao.findAll()), HttpStatus.OK);
        } else if (user.getRole() == 4) {
            return new ResponseEntity<>(new JSONResponse(200, cardChangeOrderDao.findByOperateUsername(user.getUsername())), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "该用户无法查看内部码账变订单。")), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "修改内部卡账变订单",actionType = "3")
    @ApiOperation(value = "修改内部卡账变订单", notes = "修改内部卡账变订单")
    @RequestMapping(value = "internalaccountchange/card/{uid}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> UpdateCardChangeOrder(@PathVariable("uid") int id, double real_money, double card_balance) {
        CardChangeOrder cardChangeOrder = cardChangeOrderDao.findById(id);
        cardChangeOrder.setCardBalance(card_balance);
        cardChangeOrder.setMoney_in(real_money);
        cardChangeOrder.setState(WithdrewState.SUCCESS);
        changeOrderDataService.saveCardChangeOrder(cardChangeOrder);
        //更新公司银行卡余额
        CompanyCard companyCard = companyCardDao.findCompanyCardByCardNumber(cardChangeOrder.getCardNumber_in());
        companyCard.setBalance(companyCard.getBalance() + real_money);
        companyCardDataService.saveCompanyCard(companyCard);
        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("更新内部卡账变订单以及公司银行卡余额成功")), HttpStatus.OK);
    }

//    @ApiOperation(value = "查看内部卡转入账变订单", notes = "查看内部卡转入账变订单")
//    @RequestMapping(value = "internalaccountchange/cardIn/history", method = RequestMethod.POST)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> GetCardChangeOrderById() {
//        return new ResponseEntity<>(new JSONResponse(200, changeBlService.getAllCardChangeHistory()), HttpStatus.OK);
//
//    }
}
