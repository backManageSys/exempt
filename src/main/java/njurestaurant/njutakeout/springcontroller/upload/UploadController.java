package njurestaurant.njutakeout.springcontroller.upload;

import io.swagger.annotations.*;
import njurestaurant.njutakeout.blservice.upload.ImageUploadBlService;
import njurestaurant.njutakeout.exception.FoodIdDoesNotExistException;
import njurestaurant.njutakeout.exception.SystemException;
import njurestaurant.njutakeout.publicdatas.account.Role;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.upload.UploadImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@PreAuthorize(value = "hasRole('" + Role.RESTAURANT_NAME + "')")
@RestController
public class UploadController {
    private final ImageUploadBlService imageUploadBlService;

    @Autowired
    public UploadController(ImageUploadBlService imageUploadBlService) {
        this.imageUploadBlService = imageUploadBlService;
    }


    @Authorization(value = "商户")
    @ApiOperation(value = "商户上传图片", notes = "商户上传菜品图片，传输时限为10min")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "multipartFile", value = "图片", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "foodId", value = "菜品ID", required = true, dataType = "int", paramType = "path")
    })
    @RequestMapping(value = "/upload/food/{foodId}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Image uploaded", response = UploadImageResponse.class),
            @ApiResponse(code = 403, message = "Upload session timed out", response = WrongResponse.class),
            @ApiResponse(code = 404, message = "Upload session id not exist", response = WrongResponse.class),
            @ApiResponse(code = 503, message = "Failure", response = WrongResponse.class)
    })
    public ResponseEntity<Response> uploadFiles(@PathVariable("foodId") int foodId, @RequestParam("file") MultipartFile multipartFile) {
        try {
            return new ResponseEntity<>(imageUploadBlService.uploadFiles(foodId, multipartFile), HttpStatus.CREATED);
        } catch (SystemException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (FoodIdDoesNotExistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
        }
    }
}
