package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.company.PostAndPermission;

import java.util.List;

public interface PostAndPermissionDataService {
    /**
     * save the permission of the post
     * @param postAndPermission the permission of the post
     */
    PostAndPermission savePostAndPermission(PostAndPermission postAndPermission);

    void deleteByPost(String post);

    List<PostAndPermission> findPostAndPermissionsByPost(String post);

    List<PostAndPermission> savePostAndPermissions(List<PostAndPermission> postAndPermissions);

    List<PostAndPermission> findAllPostAndPermissions();
}
