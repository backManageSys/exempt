package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.PermissionBlService;
import njurestaurant.njutakeout.dataservice.company.PermissionDataService;
import njurestaurant.njutakeout.entity.company.Permission;
import njurestaurant.njutakeout.exception.IsExistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionBlServiceImpl implements PermissionBlService {
    private final PermissionDataService permissionDataService;

    @Autowired
    public PermissionBlServiceImpl(PermissionDataService permissionDataService) {
        this.permissionDataService = permissionDataService;
    }

    @Override
    public Permission addPermission(String name) throws IsExistentException {
        Permission permission = permissionDataService.findPermissionByName(name);
        if(permission != null) {
            throw new IsExistentException();
        }
        return permissionDataService.savePermission(new Permission(name));
    }

    @Override
    public void delPermissionById(int id) {
        permissionDataService.deleteById(id);
    }

    @Override
    public List<Permission> getPermissions() {
        return permissionDataService.findAll();
    }
}
