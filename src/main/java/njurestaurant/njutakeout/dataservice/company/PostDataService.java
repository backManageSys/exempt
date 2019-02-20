package njurestaurant.njutakeout.dataservice.company;

import njurestaurant.njutakeout.entity.company.Post;

import java.util.List;

public interface PostDataService {
    Post findPostById(int id);

    Post findPostByName(String name);

    void deletePostById(int id);

    void deletePostByName(String name);

    Post savePost(Post post);

    List<Post> findAll();

    List<Post> saveAllPosts(List<Post> posts);

}
