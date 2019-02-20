package njurestaurant.njutakeout.springcontroller.company;

import io.swagger.annotations.*;
import njurestaurant.njutakeout.Log.SystemControllerLog;
import njurestaurant.njutakeout.bl.order.TransactionBlServiceImpl;
import njurestaurant.njutakeout.blservice.account.MerchantBlService;
import njurestaurant.njutakeout.blservice.account.SupplierBlService;
import njurestaurant.njutakeout.blservice.account.UserBlService;
import njurestaurant.njutakeout.blservice.company.*;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.account.Supplier;
import njurestaurant.njutakeout.entity.company.*;
import njurestaurant.njutakeout.entity.company.System;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.company.*;
import njurestaurant.njutakeout.parameters.company.MerchantApprovalParameters;
import njurestaurant.njutakeout.publicdatas.account.MerchantState;
import njurestaurant.njutakeout.publicdatas.account.SupplierState;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.*;
import njurestaurant.njutakeout.response.user.UserAddResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {

    private final TeamBlService teamBlService;
    private final ReceiptCodeBlService receiptCodeBlService;
    private final CompanyCardBlService companyCardBlService;
    private final PostAndPermissionBlService postAndPermissionBlService;
    private final AllocationRecordBlService allocationRecordBlService;
    private final MerchantBlService merchantBlService;
    private final SupplierBlService supplierBlService;
    private final PostBlService postBlService;
    private final PermissionBlService permissionBlService;
    private final SystemBlService systemBlService;
    private final UserBlService userBlService;
    private static String announcement;

    @Autowired
    public CompanyController(TeamBlService teamBlService, ReceiptCodeBlService receiptCodeBlService, CompanyCardBlService companyCardBlService, PostAndPermissionBlService postAndPermissionBlService, AllocationRecordBlService allocationRecordBlService, MerchantBlService merchantBlService, SupplierBlService supplierBlService, PostBlService postBlService, PermissionBlService permissionBlService, SystemBlService systemBlService, UserBlService userBlService) {
        this.teamBlService = teamBlService;
        this.receiptCodeBlService = receiptCodeBlService;
        this.companyCardBlService = companyCardBlService;
        this.postAndPermissionBlService = postAndPermissionBlService;
        this.allocationRecordBlService = allocationRecordBlService;
        this.merchantBlService = merchantBlService;
        this.supplierBlService = supplierBlService;
        this.postBlService = postBlService;
        this.permissionBlService = permissionBlService;
        this.systemBlService = systemBlService;
        this.userBlService = userBlService;
    }

    @ApiOperation(value = "公告管理", notes = "获取公告")
    @RequestMapping(value = "company/announcement/get", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = System.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> GetAnnouncement() {
        return new ResponseEntity<>(new JSONResponse(200, CompanyController.announcement), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "修改公告",actionType = "3")
    @ApiOperation(value = "修改公告", notes = "修改公告")
    @RequestMapping(value = "company/announcement/set", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = System.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> SetAnnouncement(@RequestParam String announcement) {
        CompanyController.announcement = announcement;
        return new ResponseEntity<>(new JSONResponse(200, CompanyController.announcement), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "新增团队",actionType = "1")
    @ApiOperation(value = "新增团队", notes = "公司管理员新增团队")
    @RequestMapping(value = "company/team/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TeamAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addTeam(@RequestBody TeamAddParameters teamAddParameters) {
        try {
            TeamAddResponse teamAddResponse = teamBlService.addTeam(teamAddParameters);
            return new ResponseEntity<>(new JSONResponse(200, teamAddResponse), HttpStatus.OK);
        } catch (IsExistentException e) {
            return new ResponseEntity<>(new JSONResponse(10110, "The team name is existent!", e.getResponse()), HttpStatus.OK);
        } catch (BlankInputException e) {
            return new ResponseEntity<>(new JSONResponse(10120, "The team name is null or size 0!", e.getResponse()), HttpStatus.OK);
        }
    }
    @SystemControllerLog(descrption = "新增收款码",actionType = "1")
    @ApiOperation(value = "新增收款码", notes = "管理员新增收款码")
    @RequestMapping(value = "company/code/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addReceiptCode(@RequestBody ReceiptCodeAddParameters receiptCodeAddParameters) {
        ReceiptCode receiptCode = receiptCodeBlService.addReceiptCode(new ReceiptCode(receiptCodeAddParameters.getTeam(), receiptCodeAddParameters.getType(), receiptCodeAddParameters.getDuration(), receiptCodeAddParameters.getPriority(), receiptCodeAddParameters.getInfo(), receiptCodeAddParameters.getNumber()));
        return new ResponseEntity<>(new JSONResponse(200, receiptCode.getId()), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "新增银行卡",actionType = "1")
    @ApiOperation(value = "新增银行卡", notes = "公司管理员新增银行卡")
    @RequestMapping(value = "company/card/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CompanyCardAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addCompanyCard(@RequestBody CompanyCardAddParameters companyCardAddParameters) {
        if (StringUtils.isBlank(companyCardAddParameters.getNumber())) {
            return new ResponseEntity<>(new JSONResponse(10110, new WrongResponse(10110, "the card number is null.")), HttpStatus.OK);
        }
        CompanyCard companyCard = new CompanyCard(companyCardAddParameters.getName(), companyCardAddParameters.getBank(), companyCardAddParameters.getNumber(), companyCardAddParameters.getBalance(), companyCardAddParameters.getAttribution(), companyCardAddParameters.getRelation(), companyCardAddParameters.getStatus());
        try {
            CompanyCardAddResponse companyCardAddResponse = companyCardBlService.addCompanyCard(companyCard);
            return new ResponseEntity<>(new JSONResponse(200, companyCardAddResponse), HttpStatus.OK);
        } catch (IsExistentException e) {
            return new ResponseEntity<>(new JSONResponse(10110, "the card number is existent.", e.getResponse()), HttpStatus.OK);
        }
    }
    @SystemControllerLog(descrption = "权限分配",actionType = "3")
    @ApiOperation(value = "权限分配", notes = "管理员分配权限")
    @RequestMapping(value = "company/permission/allocate", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PostAndPermissionResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addPermissionOfPost(@RequestBody PermissionsAllocationParameters permissionsAllocationParameters) {
        return new ResponseEntity<>(new JSONResponse(200, postAndPermissionBlService.addPostAndPermissions(permissionsAllocationParameters.getPost(), permissionsAllocationParameters.getPermissions())), HttpStatus.OK);
    }

    @ApiOperation(value = "查看权限", notes = "管理员查看岗位的权限")
    @RequestMapping(value = "company/permission", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PostAndPermissionResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showPermissionOfPost(@RequestParam("post") String post) {
        if (StringUtils.isBlank(post)) {
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "post is null.")), HttpStatus.OK);
        }
        return new ResponseEntity<>(new JSONResponse(200, postAndPermissionBlService.getPostAndPermissionsByPost(post)), HttpStatus.OK);
    }

    @ApiOperation(value = "查看全部岗位权限", notes = "管理员查看全部岗位的权限")
    @RequestMapping(value = "company/permissions", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PostAndPermissionResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showPermissions() {
        return new ResponseEntity<>(new JSONResponse(200, postAndPermissionBlService.getAll()), HttpStatus.OK);
    }

    @ApiOperation(value = "收款码列表", notes = "财务管理收款码列表")
    @RequestMapping(value = "company/codes", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReceiptCodeLoadResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showReceiptCode() {
        return new ResponseEntity<>(new JSONResponse(200, receiptCodeBlService.loadReceiptCodes()), HttpStatus.OK);
    }

    @ApiOperation(value = "银行卡列表", notes = "财务管理银行卡列表")
    @RequestMapping(value = "company/cards", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CompanyCardLoadResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showBankCards() {
        return new ResponseEntity<>(new JSONResponse(200, companyCardBlService.loadAllCompanyCards()), HttpStatus.OK);
    }

    @ApiOperation(value = "团队列表", notes = "查看全部团队列表")
    @RequestMapping(value = "company/teams", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TeamAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showTeamsNumber() {
//        List<Integer> teamsNumber = teamBlService.loadAllTeam().stream().map(x -> x.getId()).collect(Collectors.toList());
        return new ResponseEntity<>(new JSONResponse(200, teamBlService.loadAllTeam()), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "删除团队",actionType = "2")
    @ApiOperation(value = "删除团队", notes = "删除某个团队列表")
    @RequestMapping(value = "company/team/delete/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TeamAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deleteTeam(@PathVariable("id") int id, @RequestParam("verifyCode") String verifyCode) {
        try {
            return new ResponseEntity<>(new JSONResponse(200, teamBlService.delTeamById(id, verifyCode)), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, e.getResponse()), HttpStatus.OK);
        } catch (TeamVerifyCodeWrongException e) {
            return new ResponseEntity<>(new JSONResponse(10200, e.getResponse()), HttpStatus.OK);
        }
    }
    @SystemControllerLog(descrption = "修改团队",actionType = "3")
    @ApiOperation(value = "修改团队", notes = "管理员修改团队内容")
    @RequestMapping(value = "company/team/update/{id}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TeamAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> modifyTeam(@PathVariable("id") int id, @RequestBody TeamAddParameters teamAddParameters) {
        try {
            if (StringUtils.isBlank(teamAddParameters.getTeamName())) {
                return new ResponseEntity<>(new JSONResponse(200, new WrongResponse(10120, "团队名不能为空")), HttpStatus.OK);
            }
            return new ResponseEntity<>(new JSONResponse(200, teamBlService.updateTeam(teamAddParameters, id)), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, e.getResponse()), HttpStatus.OK);
        } catch (IsExistentException e) {
            return new ResponseEntity<>(new JSONResponse(10110, e.getResponse()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "团队操作验证", notes = "管理员修改团队内容时需要验证验证码")
    @RequestMapping(value = "company/team/verify/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TeamAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> verifyTeamPasswd(@PathVariable("id") int id, @RequestParam("verifyCode") String verifyCode) {
        try {
            return new ResponseEntity<>(new JSONResponse(200, teamBlService.verifyTeamCode(id, verifyCode)), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, e.getResponse()), HttpStatus.OK);
        } catch (TeamVerifyCodeWrongException e) {
            return new ResponseEntity<>(new JSONResponse(10200, e.getResponse()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "根据团队名验证团队操作", notes = "管理员删除银行卡时需要验证验证码")
    @RequestMapping(value = "company/team/verifybyteamname", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TeamAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> verifyTeamPasswdByTeamName(@RequestParam("teamName") String teamName, @RequestParam("verifyCode") String verifyCode) {
        try {
            return new ResponseEntity<>(new JSONResponse(200, teamBlService.verifyTeamCodeByTeamName(teamName, verifyCode)), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, "团队名不存在"), HttpStatus.OK);
        } catch (TeamVerifyCodeWrongException e) {
            return new ResponseEntity<>(new JSONResponse(10200, e.getResponse()), HttpStatus.OK);
        }
    }
    @SystemControllerLog(descrption = "审批商户账号开通",actionType = "3")
    @ApiOperation(value = "审批商户账号开通", notes = "管理员审批待审批的商户账号")
    @RequestMapping(value = "company/approval/merchant/{mid}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> merchantApproval(@PathVariable("mid") int mid, @RequestBody MerchantApprovalParameters merchantApprovalParameters) {
        return new ResponseEntity<>(new JSONResponse(200, merchantBlService.ApprovalMerchant(mid, merchantApprovalParameters)), HttpStatus.OK);
    }

    @ApiOperation(value = "待审批商户账号", notes = "管理员查看待审批的商户账号列表")
    @RequestMapping(value = "company/approval/merchants", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Merchant.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> approvalMerchants() {
        List<Merchant> list = merchantBlService.findMerchantsByState("停用");
        List<Merchant> list1 = merchantBlService.findMerchantsByState("申请启用");
        list.addAll(list1);
        return new ResponseEntity<>(new JSONResponse(200, list), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "审批供码账号",actionType = "3")
    @ApiOperation(value = "审批供码账号", notes = "管理员审批待审批供码用户账号")
    @RequestMapping(value = "company/approval/supplier/{sid}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> supplierApproval(@PathVariable("sid") int sid, @RequestBody SupplierApprovalParameters supplierApprovalParameters) {
        return new ResponseEntity<>(new JSONResponse(200, supplierBlService.approvalSupplier(sid, supplierApprovalParameters)), HttpStatus.OK);
    }

    @ApiOperation(value = "待审批供码账号", notes = "管理员查看待审批的供码用户列表")
    @RequestMapping(value = "company/approval/suppliers", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Supplier.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> approvalSuppliers() {

        return new ResponseEntity<>(new JSONResponse(200, supplierBlService.findSupplierByState("停用")), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "删除银行卡",actionType = "2")
    @ApiOperation(value = "删除银行卡", notes = "删除银行卡")
    @RequestMapping(value = "company/card/delete/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deleteBankCard(@PathVariable("id") int id) {
        companyCardBlService.delCompanyCardById(id);
        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete success.")), HttpStatus.OK);
    }

//    @ApiOperation(value = "公司银行卡信息", notes = "查看银行卡详细信息")
//    @RequestMapping(value = "company/card/info/{id}", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> showBankCard(@PathVariable("id") int id) {
//        CompanyCard companyCard = companyCardBlService.loadCompanyCardById(id);
//        if(companyCard == null || companyCard.getId() == 0) {
//            return new ResponseEntity<>(new JSONResponse(10150, new WrongResponse(10130, "Id isn't existent.")), HttpStatus.OK);
//        } else
//            return new ResponseEntity<>(new JSONResponse(200, companyCard), HttpStatus.OK);
//    }
    @SystemControllerLog(descrption = "删除收款码",actionType = "2")
    @ApiOperation(value = "删除收款码", notes = "删除收款码")
    @RequestMapping(value = "company/code/delete/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deleteReceiptCode(@PathVariable("id") int id) {
        receiptCodeBlService.delReceiptCode(id);
        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete success.")), HttpStatus.OK);
    }

    @ApiOperation(value = "岗位列表", notes = "查看全部岗位")
    @RequestMapping(value = "company/post/list", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Post.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showAllPosts() {
        return new ResponseEntity<>(new JSONResponse(200, postBlService.getPosts()), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "删除岗位",actionType = "2")
    @ApiOperation(value = "删除岗位", notes = "删除公司的岗位")
    @RequestMapping(value = "company/post/delete/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deletePost(@PathVariable("id") int id) {
        postBlService.delPostById(id);
        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete success.")), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "增加岗位",actionType = "1")
    @ApiOperation(value = "增加岗位", notes = "管理员增加公司的岗位")
    @RequestMapping(value = "company/post/add", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Post.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deletePost(@RequestParam("post") String post) {
        try {
            Post p = postBlService.addPost(post);
            return new ResponseEntity<>(new JSONResponse(200, p), HttpStatus.OK);
        } catch (IsExistentException e) {
            return new ResponseEntity<>(new JSONResponse(10110, e.getResponse()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "权限列表", notes = "管理员查看全部权限")
    @RequestMapping(value = "company/permission/list", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Permission.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showAllPermissions() {
        return new ResponseEntity<>(new JSONResponse(200, permissionBlService.getPermissions()), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "删除权限",actionType = "2")
    @ApiOperation(value = "删除权限", notes = "管理员删除某个权限")
    @RequestMapping(value = "company/permission/delete/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deletePermission(@PathVariable("id") int id) {
        permissionBlService.delPermissionById(id);
        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete success.")), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "增加权限",actionType = "1")
    @ApiOperation(value = "增加权限", notes = "管理员增加岗位权限")
    @RequestMapping(value = "company/permission/add", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Permission.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> addPermission(@RequestParam("permission") String permission) {
        try {
            Permission p = permissionBlService.addPermission(permission);
            return new ResponseEntity<>(new JSONResponse(200, p), HttpStatus.OK);
        } catch (IsExistentException e) {
            return new ResponseEntity<>(new JSONResponse(10110, e.getResponse()), HttpStatus.OK);
        }
    }

//    @ApiOperation(value = "增加系统管理", notes = "管理员增加系统管理内容")
//    @RequestMapping(value = "company/sys/add", method = RequestMethod.POST)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = UserAddResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> addSystemManager(@RequestBody SystemAddParameters systemAddParameters) {
//        try {
//            if(StringUtils.isBlank(systemAddParameters.getTitle())) {
//                return new ResponseEntity<>(new JSONResponse(10120, new BlankInputException().getResponse()), HttpStatus.OK);
//            }
//            return new ResponseEntity<>(new JSONResponse(200, systemBlService.addSystem(new System(systemAddParameters.getTitle())).getId()), HttpStatus.OK);
//        } catch (IsExistentException e) {
//            return new ResponseEntity<>(new JSONResponse(10110, e.getResponse()), HttpStatus.OK);
//        }
//    }

//    @ApiOperation(value = "删除系统管理", notes = "管理员删除系统管理内容")
//    @RequestMapping(value = "company/sys/delete/{id}", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> deleteSysManager(@PathVariable("id")int id) {
//        systemBlService.delSystemById(id);
//        return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("delete success")), HttpStatus.OK);
//    }
    @SystemControllerLog(descrption = "修改系统管理",actionType = "3")
    @ApiOperation(value = "修改系统管理", notes = "管理员修改系统管理内容")
    @RequestMapping(value = "company/sys/update", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> updatePermission(@RequestBody SystemAddParameters systemAddParameters) {
        if (StringUtils.isBlank(systemAddParameters.getTitle())) {
            return new ResponseEntity<>(new JSONResponse(10120, new BlankInputException().getResponse()), HttpStatus.OK);
        }
        systemBlService.updateSystem(systemAddParameters.getTitle());
        return new ResponseEntity<>(new JSONResponse(200, "更新成功"), HttpStatus.OK);
    }

    @ApiOperation(value = "系统列表", notes = "管理员查看全部系统管理内容")
    @RequestMapping(value = "company/sys/list", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = System.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showAllSys() {
        return new ResponseEntity<>(new JSONResponse(200, systemBlService.findAllSystem()), HttpStatus.OK);
    }

    @ApiOperation(value = "风控管理", notes = "管理员查看二维码失效时间")
    @RequestMapping(value = "company/riskcontrol/get", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = System.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> GetRiskcontrol() {
        return new ResponseEntity<>(new JSONResponse(200, TransactionBlServiceImpl.getTime()), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "管理员修改二维码失效时间",actionType = "3")
    @ApiOperation(value = "管理员修改二维码失效时间", notes = "管理员修改二维码失效时间")
    @RequestMapping(value = "company/riskcontrol/set", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = System.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> SetRiskcontrol(@RequestParam double newtime) {
        TransactionBlServiceImpl.setTime(newtime);
        return new ResponseEntity<>(new JSONResponse(200, TransactionBlServiceImpl.getTime()), HttpStatus.OK);
    }
//    @ApiOperation(value = "查看某个系统", notes = "管理员查看某个系统管理内容")
//    @RequestMapping(value = "company/sys/{id}", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = System.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> showSys(@PathVariable("id") int id) {
//        try {
//            return new ResponseEntity<>(new JSONResponse(200, systemBlService.findSystemById(id)), HttpStatus.OK);
//        } catch (WrongIdException e) {
//            return new ResponseEntity<>(new JSONResponse(200, e.getResponse()), HttpStatus.OK);
//        }
//    }

//    @ApiOperation(value = "收款码信息", notes = "查看收款码详细信息")
//    @RequestMapping(value = "company/code/info/{id}", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> showReceiptCode(@PathVariable("id") int id) {
//        ReceiptCode receiptCode = receiptCodeBlService.findReceiptCodeById(id);
//        if(receiptCode == null || receiptCode.getId() == 0) {
//            return new ResponseEntity<>(new JSONResponse(10150, new WrongResponse(10130, "Id isn't existent.")), HttpStatus.OK);
//        } else
//            return new ResponseEntity<>(new JSONResponse(200, receiptCode), HttpStatus.OK);
//    }
}
