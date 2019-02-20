package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.company.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDao extends JpaRepository<Post, Integer> {
    Post findPostByPost(String post);

    void deletePostByPost(String post);
}
