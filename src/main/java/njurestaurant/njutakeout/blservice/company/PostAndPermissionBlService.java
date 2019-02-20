package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.PostAndPermission;
import njurestaurant.njutakeout.response.company.PostAndPermissionResponse;

import java.util.List;

public interface PostAndPermissionBlService {
//    /**
//     * add the permission of post
//     * @param postAndPermission
//     * @return
//     */
//    PostAndPermissionResponse addPostAndPermission(PostAndPermission postAndPermission);

    void delPermissionByPost(String post);

    List<PostAndPermission> addPostAndPermissions(String post, List<String> permission);

    PostAndPermissionResponse getPostAndPermissionsByPost(String post);

    List<PostAndPermissionResponse> getAll();
}
