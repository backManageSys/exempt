package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.PostAndPermissionBlService;
import njurestaurant.njutakeout.dataservice.account.PostAndPermissionDataService;
import njurestaurant.njutakeout.dataservice.company.PostDataService;
import njurestaurant.njutakeout.entity.company.Post;
import njurestaurant.njutakeout.entity.company.PostAndPermission;
import njurestaurant.njutakeout.response.company.PostAndPermissionResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostAndPermissionBlServiceImpl implements PostAndPermissionBlService {
    private final PostAndPermissionDataService postAndPermissionDataService;
    private final PostDataService postDataService;

    @Autowired
    public PostAndPermissionBlServiceImpl(PostAndPermissionDataService postAndPermissionDataService, PostDataService postDataService) {
        this.postAndPermissionDataService = postAndPermissionDataService;
        this.postDataService = postDataService;
    }


//    /**
//     *
//     * @param postAndPermission
//     * @return
//     */
//    @Override
//    public PostAndPermissionResponse addPostAndPermission(PostAndPermission postAndPermission) {
//        return new PostAndPermissionResponse(postAndPermissionDataService.savePostAndPermission(postAndPermission).getId());
//    }

    @Override
    public void delPermissionByPost(String post) {
        postAndPermissionDataService.deleteByPost(post);
    }

    @Override
    public List<PostAndPermission> addPostAndPermissions(String post, List<String> permission) {
        postAndPermissionDataService.deleteByPost(post);
        List<PostAndPermission> postAndPermissionList = new ArrayList<>();
        if(permission != null) {
            for(String p : permission) {
                if(StringUtils.isNotBlank(p)) {
                    PostAndPermission per = new PostAndPermission(post, p);
                    postAndPermissionList.add(per);
                }
            }
        }
        return postAndPermissionDataService.savePostAndPermissions(postAndPermissionList);
    }

    @Override
    public PostAndPermissionResponse getPostAndPermissionsByPost(String post) {
        List<PostAndPermission> postAndPermissionList = postAndPermissionDataService.findPostAndPermissionsByPost(post);
        List<String> permissions = new ArrayList<>();
        if(postAndPermissionList.size() ==0) return null;
        for(PostAndPermission p : postAndPermissionList) {
            permissions.add(p.getPermission());
        }
        Post p = postDataService.findPostByName(post);
        return new PostAndPermissionResponse(post, permissions, p.getId());
    }

    @Override
    public List<PostAndPermissionResponse> getAll() {
        List<PostAndPermission> postAndPermissionList = postAndPermissionDataService.findAllPostAndPermissions();
        Map<String, List<String>> postMap = new HashMap<>();
        List<Post> postList = postDataService.findAll();
        Map<String, Integer> postIdMap = new HashMap<>();
        for(Post p : postList) {
            postIdMap.put(p.getPost(), p.getId());
        }
        List<PostAndPermissionResponse> result = new ArrayList<>();
        if(postAndPermissionList.size() == 0)   return result;
        for(PostAndPermission p : postAndPermissionList) {
            if(postMap.containsKey(p.getPost())) {
                List<String> permissions = postMap.get(p.getPost());
                if(permissions == null || permissions.size() == 0) {
                    permissions = new ArrayList<>();
                }
                permissions.add(p.getPermission());
            } else {
                List<String> permissions = new ArrayList<>();
                permissions.add(p.getPermission());
                postMap.put(p.getPost(), permissions);
            }
        }
        for(Map.Entry<String, List<String>> entry : postMap.entrySet()) {
            if(postIdMap.containsKey(entry.getKey())) {
                PostAndPermissionResponse response = new PostAndPermissionResponse(entry.getKey(), entry.getValue(), postIdMap.get(entry.getKey()));
                result.add(response);
            }
        }
        return result;
    }
}
