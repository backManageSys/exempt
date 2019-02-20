package njurestaurant.njutakeout.springcontroller.report;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.blservice.order.PlatformOrderBlService;
import njurestaurant.njutakeout.blservice.report.ReportBlService;
import njurestaurant.njutakeout.exception.WrongInputException;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.order.OrderListResponse;
import njurestaurant.njutakeout.response.report.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class ReportController {

    private final ReportBlService reportBlService;

    @Autowired
    public ReportController(ReportBlService reportBlService) {
        this.reportBlService = reportBlService;
    }

    @ApiOperation(value = "商户报表", notes = "管理员查看商户全部报表")
    @RequestMapping(value = "report/merchant", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MerchantReportResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> merchantReport(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            return new ResponseEntity<>(new JSONResponse(200, reportBlService.getReportOfMerchant(startDate,endDate)), HttpStatus.OK);
        } catch (WrongInputException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "输入的日期格式错误。")), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "代理报表", notes = "管理员查看代理商全部报表")
    @RequestMapping(value = "report/agent", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = AgentReportResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> agentReport(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            return new ResponseEntity<>(new JSONResponse(200, reportBlService.getReportOfAgent(startDate, endDate)), HttpStatus.OK);
        } catch (WrongInputException e) {
            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "输入的日期格式错误。")), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "收款码报表", notes = "管理员查看收款码的报表")
    @RequestMapping(value = "report/receiptCode", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReceiptCodeReportResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> codeReport(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            return new ResponseEntity<>(new JSONResponse(200, reportBlService.getReportOfReceiptCode(startDate, endDate)), HttpStatus.OK);
        } catch (WrongInputException e) {
            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "输入的日期格式错误。")), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "资金报表", notes = "管理员查看公司流入流出资金报表")
    @RequestMapping(value = "report/funding", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FundingReportResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> fundingReport(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            return new ResponseEntity<>(new JSONResponse(200, reportBlService.getReportOfFunding(startDate, endDate)), HttpStatus.OK);
        } catch (WrongInputException e) {
            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "输入的日期格式错误。")), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "团队（供码用户）报表", notes = "管理员查看公司供码用户流水报表")
    @RequestMapping(value = "report/supplier", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SupplierReportResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> supplierReport(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            return new ResponseEntity<>(new JSONResponse(200, reportBlService.getReportOfSupplier(startDate, endDate)), HttpStatus.OK);
        } catch (WrongInputException e) {
            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "输入的日期格式错误。")), HttpStatus.OK);
        }
    }

}
