package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.company.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionDao extends JpaRepository<Permission, Integer> {
    Permission findPermissionByPermission(String permission);

    void deletePermissionByPermission(String permission);
}
