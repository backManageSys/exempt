package njurestaurant.njutakeout.springcontroller.account;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.Log.ExecutionResult;
import njurestaurant.njutakeout.Log.ReturnCode;
import njurestaurant.njutakeout.Log.SystemControllerLog;
import njurestaurant.njutakeout.blservice.account.PersonalCardBlService;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.user.PersonalCardAddParameters;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.user.PersonalCardAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonalCardController {
    private final PersonalCardBlService personalCardBlService;

    @Autowired
    public PersonalCardController(PersonalCardBlService personalCardBlService) {
        this.personalCardBlService = personalCardBlService;
    }
    @ApiOperation(value = "新增银行卡", notes = "用户个人银行卡增加")
    @RequestMapping(value = "usr/card/add", method = RequestMethod.POST)
    @SystemControllerLog(descrption = "用户个人新增银行卡",actionType = "1")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PersonalCardAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addPersonalCard(@RequestBody PersonalCardAddParameters personalCardAddParameters) {
    //    ExecutionResult result = new ExecutionResult();
        try {
            PersonalCardAddResponse personalCardAddResponse = personalCardBlService.addPersonalCard(personalCardAddParameters);
//            result.setResultCode(ReturnCode.RES_SUCCESS);
//            result.setMsg("成功！");
            return new ResponseEntity<>(new JSONResponse(200, personalCardAddResponse), HttpStatus.OK);
        } catch (IsExistentException e) {
//            result.setResultCode(ReturnCode.RES_FAILED);
//            result.setMsg("失败！");
//            result.setExceptioncode(e.getResponse().getInfoCode());
//            result.setExceptiondetail(e.getResponse().getDescription());
            return new ResponseEntity<>(new JSONResponse(10110, new WrongResponse(10110, "已有该卡号。")), HttpStatus.OK);
        } catch (WrongIdException e) {
//            result.setResultCode(ReturnCode.RES_FAILED);
//            result.setMsg("失败！");
//            result.setExceptioncode(e.getResponse().getInfoCode());
//            result.setExceptiondetail(e.getResponse().getDescription());
            return new ResponseEntity<>(new JSONResponse(10160, e.getResponse()), HttpStatus.OK);
        }
    }
//
//    @ApiOperation(value = "查看银行卡", notes = "用户查看个人银行卡详情")
//    @RequestMapping(value = "usr/card/{id}", method = RequestMethod.POST)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = PersonalCard.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> showPersonalCard(@PathVariable("id")int id) {
//        PersonalCard personalCard = personalCardBlService.getPersonalCardById(id);
//        if(personalCard == null || personalCard.getId() == 0) {
//            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "Wrong id.")), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(new JSONResponse(200, personalCard), HttpStatus.OK);
//        }
//    }

    @ApiOperation(value = "个人银行卡列表", notes = "用户个人银行卡列表")
    @RequestMapping(value = "usr/cards/{uid}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PersonalCard.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showMyPersonalCard(@PathVariable("uid")int uid) {
        try {
            List<PersonalCard> personalCards = personalCardBlService.getMyPersonalCardByUserId(uid);
            return new ResponseEntity<>(new JSONResponse(200, personalCards), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, e.getResponse()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "全部个人银行卡列表", notes = "管理员查看个人银行卡")
    @RequestMapping(value = "usr/cards", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PersonalCard.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showPersonalCards() {
        return new ResponseEntity<>(new JSONResponse(200, personalCardBlService.getAllCards()), HttpStatus.OK);
    }
}
