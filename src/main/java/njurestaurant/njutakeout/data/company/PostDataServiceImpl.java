package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.company.PostDao;
import njurestaurant.njutakeout.dataservice.company.PostDataService;
import njurestaurant.njutakeout.entity.company.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostDataServiceImpl implements PostDataService {
    private final PostDao postDao;

    @Autowired
    public PostDataServiceImpl(PostDao postDao) {
        this.postDao = postDao;
    }

    @Override
    public Post findPostById(int id) {
        Optional<Post> post = postDao.findById(id);
        if(post.isPresent())    return post.get();
        else return null;
    }

    @Override
    public Post findPostByName(String name) {
        return postDao.findPostByPost(name);
    }

    @Override
    public void deletePostById(int id) {
        postDao.deleteById(id);
    }

    @Override
    public void deletePostByName(String name) {
        postDao.deletePostByPost(name);
    }

    @Override
    public Post savePost(Post post) {
        return postDao.save(post);
    }

    @Override
    public List<Post> findAll() {
        return postDao.findAll();
    }

    @Override
    public List<Post> saveAllPosts(List<Post> posts) {
        return null;
    }
}
