package njurestaurant.njutakeout.blservice.upload;

import njurestaurant.njutakeout.exception.FoodIdDoesNotExistException;
import njurestaurant.njutakeout.exception.SystemException;
import njurestaurant.njutakeout.response.upload.UploadImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadBlService {
    /**
     * Upload the image of the food
     *
     * @param foodId
     * @param multipartFile
     * @return the url of the image
     */
    UploadImageResponse uploadFiles(int foodId, MultipartFile multipartFile) throws SystemException, FoodIdDoesNotExistException;
}
