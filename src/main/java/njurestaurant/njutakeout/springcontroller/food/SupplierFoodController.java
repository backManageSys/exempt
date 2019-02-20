//package njurestaurant.njutakeout.springcontroller.food;
//
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import njurestaurant.njutakeout.blservice.food.FoodBlService;
//import njurestaurant.njutakeout.exception.FoodIdDoesNotExistException;
//import njurestaurant.njutakeout.exception.PortDoesNotExistException;
//import njurestaurant.njutakeout.parameters.food.*;
//import njurestaurant.njutakeout.publicdatas.account.Role;
//import njurestaurant.njutakeout.response.Response;
//import njurestaurant.njutakeout.response.WrongResponse;
//import njurestaurant.njutakeout.response.event.EventFoodLoadResponse;
//import njurestaurant.njutakeout.response.food.*;
//import njurestaurant.njutakeout.util.UserInfoUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@PreAuthorize(value = "hasRole('" + Role.RESTAURANT_NAME + "')")
//@RestController
//public class SupplierFoodController {
//    private final FoodBlService foodBlService;
//
//    @Autowired
//    public SupplierFoodController(FoodBlService foodBlService) {
//        this.foodBlService = foodBlService;
//    }
//
//    @ApiOperation(value = "加载档口", notes = "加载所有档口")
//    @RequestMapping(value = "food/supplier/port", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = PortLoadResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> loadPorts() {
//        return new ResponseEntity<>(foodBlService.loadPorts(), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "添加档口", notes = "添加新的档口")
//    @RequestMapping(value = "food/supplier/port", method = RequestMethod.PUT)
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Success", response = FoodAddResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> addPort(@RequestBody PortAddParameters portAddParameters) {
//        return new ResponseEntity<>(foodBlService.addPort(portAddParameters), HttpStatus.CREATED);
//    }
//
//    @ApiOperation(value = "删除档口", notes = "删除档口")
//    @RequestMapping(value = "food/supplier/port/{portId}", method = RequestMethod.DELETE)
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Success", response = FoodAddResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> deletePort(@PathVariable(name = "portId") int portId) {
//        try {
//            return new ResponseEntity<>(foodBlService.deletePort(portId), HttpStatus.OK);
//        } catch (PortDoesNotExistException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @ApiOperation(value = "添加菜品", notes = "添加新的菜品信息")
//    @RequestMapping(value = "food/supplier", method = RequestMethod.PUT)
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Success", response = FoodAddResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> addFood(@RequestBody FoodAddParameters foodAddParameters) {
//        try {
//            return new ResponseEntity<>(foodBlService.addFood(foodAddParameters, UserInfoUtil.getUsername()), HttpStatus.CREATED);
//        } catch (PortDoesNotExistException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @ApiOperation(value = "加载当前餐厅菜品", notes = "加载当前餐厅的所有菜品信息")
//    @RequestMapping(value = "food/supplier", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SupplierFoodLoadResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> loadSupplierFood() {
//        return new ResponseEntity<>(foodBlService.loadSupplierFood(UserInfoUtil.getUsername()), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "加载菜品详细信息", notes = "根据id加载菜品详细信息")
//    @RequestMapping(value = "food/supplier/{foodId}", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SupplierFoodDetailResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> loadSupplierFoodById(@PathVariable(name = "foodId") int foodId) {
//        try {
//            return new ResponseEntity<>(foodBlService.loadSupplierFoodById(foodId), HttpStatus.OK);
//        } catch (FoodIdDoesNotExistException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @ApiOperation(value = "删除菜品", notes = "根据id删除菜品")
//    @RequestMapping(value = "food/supplier/{foodId}", method = RequestMethod.DELETE)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SupplierFoodDeleteResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> deleteSupplierFoodById(@PathVariable(name = "foodId") int foodId) {
//        return new ResponseEntity<>(foodBlService.deleteSupplierFoodById(foodId), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "更新菜品", notes = "根据id更新菜品")
//    @RequestMapping(value = "food/supplier/update", method = RequestMethod.POST)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SupplierFoodUpdateResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> deleteSupplierFoodById(@RequestBody SupplierFoodUpdateParameters supplierFoodUpdateParameters) {
//        try {
//            return new ResponseEntity<>(foodBlService.updateSupplierFoodById(supplierFoodUpdateParameters, UserInfoUtil.getUsername()), HttpStatus.OK);
//        } catch (PortDoesNotExistException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
//        } catch (FoodIdDoesNotExistException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @ApiOperation(value = "上架菜品", notes = "根据id上架菜品")
//    @RequestMapping(value = "food/supplier/shelf", method = RequestMethod.POST)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SupplierFoodShelfResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> shelfSupplierFood(@RequestBody SupplierFoodShelfParameters supplierFoodShelfParameters) {
//        try {
//            return new ResponseEntity<>(foodBlService.shelfSupplierFood(supplierFoodShelfParameters), HttpStatus.OK);
//        } catch (FoodIdDoesNotExistException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @ApiOperation(value = "下架菜品", notes = "根据id下架菜品")
//    @RequestMapping(value = "food/supplier/shelf", method = RequestMethod.DELETE)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = SupplierFoodShelfOffResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> shelfOffSupplierFood(@RequestBody SupplierFoodShelOffParameters supplierFoodShelfOffParameters) {
//        try {
//            return new ResponseEntity<>(foodBlService.shelfOffSupplierFood(supplierFoodShelfOffParameters), HttpStatus.OK);
//        } catch (FoodIdDoesNotExistException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @ApiOperation(value = "加载当前餐厅菜品以作活动指定", notes = "加载当前餐厅菜品以作活动指定")
//    @RequestMapping(value = "supplier/event/food", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = EventFoodLoadResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> loadEventFood() {
//        return new ResponseEntity<>(foodBlService.loadEventFood(UserInfoUtil.getUsername()), HttpStatus.OK);
//    }
//}
