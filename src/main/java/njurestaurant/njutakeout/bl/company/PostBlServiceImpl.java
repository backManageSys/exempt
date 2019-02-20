package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.PostBlService;
import njurestaurant.njutakeout.dataservice.account.PostAndPermissionDataService;
import njurestaurant.njutakeout.dataservice.company.PostDataService;
import njurestaurant.njutakeout.entity.company.Post;
import njurestaurant.njutakeout.entity.company.PostAndPermission;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostBlServiceImpl implements PostBlService {
    private final PostDataService postDataService;
    private final PostAndPermissionDataService postAndPermissionDataService;

    @Autowired
    public PostBlServiceImpl(PostDataService postDataService, PostAndPermissionDataService postAndPermissionDataService) {
        this.postDataService = postDataService;
        this.postAndPermissionDataService = postAndPermissionDataService;
    }

    @Override
    public Post addPost(String post) throws IsExistentException {
        Post p = postDataService.findPostByName(post);
        if(p != null) {
            throw new IsExistentException();
        }
        return postDataService.savePost(new Post(post));
    }

    @Override
    public Post addPosts(List<Post> posts) {
//        List<Post> postList = postDataService.findAll();
        return null;
    }

    @Override
    public void delPostById(int id) {
        postDataService.deletePostById(id);
    }

    @Override
    public List<Post> getPosts() {
        return postDataService.findAll();
    }

    @Override
    public Post updatePost(int id, String post) throws IsExistentException, WrongIdException {
        return null;
    }

}
