package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.Permission;
import njurestaurant.njutakeout.exception.IsExistentException;

import java.util.List;

public interface PermissionBlService {
    Permission addPermission(String name) throws IsExistentException;

    void delPermissionById(int id);

    List<Permission> getPermissions();
}
