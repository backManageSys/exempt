package njurestaurant.njutakeout.bl.upload;

import njurestaurant.njutakeout.blservice.upload.ImageUploadBlService;
import njurestaurant.njutakeout.dataservice.food.FoodDataService;
import njurestaurant.njutakeout.dataservice.upload.ImageDataService;
import njurestaurant.njutakeout.entity.food.Food;
import njurestaurant.njutakeout.exception.FoodIdDoesNotExistException;
import njurestaurant.njutakeout.exception.SystemException;
import njurestaurant.njutakeout.response.upload.UploadImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class ImageUploadBlServiceImpl implements ImageUploadBlService {

    private final ImageDataService imageDataService;
    private final FoodDataService foodDataService;

    @Autowired
    public ImageUploadBlServiceImpl(ImageDataService imageDataService, FoodDataService foodDataService) {
        this.imageDataService = imageDataService;
        this.foodDataService = foodDataService;
    }

    /**
     * Upload the image of the mission
     *
     * @param foodId
     * @param multipartFile
     * @return the url of the image
     */
    @Override
    public UploadImageResponse uploadFiles(int foodId, MultipartFile multipartFile) throws SystemException, FoodIdDoesNotExistException {
        try {
            Food food = foodDataService.getFoodById(foodId);
            if (food != null) {
                String url = imageDataService.uploadImage(generateImageKey(foodId), multipartFile.getBytes());
                food.setUrl(url);
                foodDataService.saveFood(food);
                return new UploadImageResponse(url);
            } else {
                throw new FoodIdDoesNotExistException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException();
        }
    }

    private String generateImageKey(int foodId) {
        return "food_" + foodId;
    }
}
