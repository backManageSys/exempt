package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.System;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;

import java.util.List;

public interface SystemBlService {

    System addSystem(System system) throws IsExistentException;

    void delSystemById(int id);

    System updateSystem(String title) ;

    System findSystemById(int id) throws WrongIdException;

    List<System> findAllSystem();
}
