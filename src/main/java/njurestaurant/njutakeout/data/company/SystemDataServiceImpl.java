package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.company.SystemDao;
import njurestaurant.njutakeout.dataservice.company.SystemDataService;
import njurestaurant.njutakeout.entity.company.System;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SystemDataServiceImpl implements SystemDataService {
    private final SystemDao systemDao;

    @Autowired
    public SystemDataServiceImpl(SystemDao systemDao) {
        this.systemDao = systemDao;
    }

    @Override
    public System saveSystem(System system) {
        return systemDao.save(system);
    }

    @Override
    public void deleteSystemById(int id) {
        systemDao.deleteById(id);
    }

    @Override
    public System findSystemById(int id) {
        Optional<System> optionalSystem = systemDao.findById(id);
        if(optionalSystem.isPresent()) return optionalSystem.get();
        else return null;
    }

    @Override
    public System findSystemByTitle(String title) {
        return systemDao.findSystemByTitle(title);
    }

    @Override
    public List<System> findAll() {
        return systemDao.findAll();
    }
}
