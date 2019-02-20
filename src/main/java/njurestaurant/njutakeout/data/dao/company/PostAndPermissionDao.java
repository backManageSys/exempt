package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.company.PostAndPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PostAndPermissionDao extends JpaRepository<PostAndPermission, String> {

    @Modifying
    @Transactional
    @Query("delete from PostAndPermission pp where pp.post = ?1")
    void deletePostAndPermissionsByPost(String post);

    List<PostAndPermission> findPostAndPermissionsByPost(String post);


}
