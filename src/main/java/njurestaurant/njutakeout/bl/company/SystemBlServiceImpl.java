package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.SystemBlService;
import njurestaurant.njutakeout.dataservice.company.SystemDataService;
import njurestaurant.njutakeout.entity.company.System;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemBlServiceImpl implements SystemBlService {
    private final SystemDataService systemDataService;

    @Autowired
    public SystemBlServiceImpl(SystemDataService systemDataService){
        this.systemDataService = systemDataService;
    }

    @Override
    public System addSystem(System system) throws IsExistentException {
        System s = systemDataService.findSystemByTitle(system.getTitle());
        if(s == null) {
            return systemDataService.saveSystem(system);
        } else {
            throw new IsExistentException();
        }
    }

    @Override
    public void delSystemById(int id) {
        systemDataService.deleteSystemById(id);
    }

    @Override
    public System updateSystem(String title) {
        System s = systemDataService.findSystemById(1);
        if (s ==null)
            systemDataService.saveSystem(new System(title));
        else {
            s.setTitle(title);
            systemDataService.saveSystem(s);
        }
        return s;
    }

    @Override
    public System findSystemById(int id) throws WrongIdException {
        System s = systemDataService.findSystemById(id);
        if(s == null) {
            throw  new WrongIdException();
        } else
            return s;
    }

    @Override
    public List<System> findAllSystem() {
        return systemDataService.findAll();
    }
}
