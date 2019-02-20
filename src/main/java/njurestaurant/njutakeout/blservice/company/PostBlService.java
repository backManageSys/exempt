package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.Post;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;

import java.util.List;

public interface PostBlService {
    Post addPost(String post) throws IsExistentException;

    Post addPosts(List<Post> posts);

    void delPostById(int id);

    List<Post> getPosts();

    Post updatePost(int id, String post) throws IsExistentException, WrongIdException;
}
