package njurestaurant.njutakeout.dataservice.company;

import njurestaurant.njutakeout.entity.company.Permission;

import java.util.List;

public interface PermissionDataService {
    Permission findPermissionById(int id);

    Permission findPermissionByName(String permission);

    List<Permission> findAll();

    void deleteById(int id);

    void deleteByName(String name);

    Permission savePermission(Permission permission);
}
