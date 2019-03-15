package njurestaurant.njutakeout.springcontroller.company;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.Log.SystemControllerLog;
import njurestaurant.njutakeout.blservice.SystemLogBlService;
import njurestaurant.njutakeout.entity.SystemLog;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.SystemLogResponse;
import njurestaurant.njutakeout.response.user.PersonalCardAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("systemLog")
public class SystemLogController {

    @Autowired
    private SystemLogBlService systemLogBlService;

    @GetMapping("getLimit")
    @ApiOperation(value = "查看系统日志", notes = "查看系统日志")
    @SystemControllerLog(descrption = "查看系统日志",actionType = "1")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PersonalCardAddResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    public ResponseEntity<Response> getLimit(@RequestParam Integer page,@RequestParam Integer size,@RequestParam String condition){
        SystemLogResponse  all = systemLogBlService.findAll(page, size,condition);
        return new ResponseEntity<>(new JSONResponse(200, all), HttpStatus.OK);
    }
}
