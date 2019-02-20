package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.company.PermissionDao;
import njurestaurant.njutakeout.dataservice.company.PermissionDataService;
import njurestaurant.njutakeout.entity.company.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionDataServiceImpl implements PermissionDataService {
    private final PermissionDao permissionDao;

    @Autowired
    public PermissionDataServiceImpl(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    @Override
    public Permission findPermissionById(int id) {
        Optional<Permission> permissionOptional = permissionDao.findById(id);
        if(permissionOptional.isPresent())  return permissionOptional.get();
        else return null;
    }

    @Override
    public Permission findPermissionByName(String permission) {
        return permissionDao.findPermissionByPermission(permission);
    }

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteByName(String name) {
        permissionDao.deletePermissionByPermission(name);
    }

    @Override
    public Permission savePermission(Permission permission) {
        return permissionDao.save(permission);
    }
}
