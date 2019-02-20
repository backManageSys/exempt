package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.company.PostAndPermissionDao;
import njurestaurant.njutakeout.dataservice.account.PostAndPermissionDataService;
import njurestaurant.njutakeout.entity.company.PostAndPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostAndPermissionDataServiceImpl implements PostAndPermissionDataService {
    private final PostAndPermissionDao postAndPermissionDao;

    @Autowired
    public PostAndPermissionDataServiceImpl(PostAndPermissionDao postAndPermissionDao) {
        this.postAndPermissionDao = postAndPermissionDao;
    }

    /**
     * save the permission of the post
     * @param postAndPermission the permission of the post
     */
    @Override
    public PostAndPermission savePostAndPermission(PostAndPermission postAndPermission) {
        return postAndPermissionDao.save(postAndPermission);
    }

    @Override
    public void deleteByPost(String post) {
        postAndPermissionDao.deletePostAndPermissionsByPost(post);
    }

    @Override
    public List<PostAndPermission> savePostAndPermissions(List<PostAndPermission> postAndPermissions) {
        return postAndPermissionDao.saveAll(postAndPermissions);
    }

    @Override
    public List<PostAndPermission> findPostAndPermissionsByPost(String post) {
        return postAndPermissionDao.findPostAndPermissionsByPost(post);
    }

    @Override
    public List<PostAndPermission> findAllPostAndPermissions() {
        return postAndPermissionDao.findAll();
    }
}
