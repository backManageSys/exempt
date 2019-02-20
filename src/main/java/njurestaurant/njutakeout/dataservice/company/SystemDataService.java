package njurestaurant.njutakeout.dataservice.company;

import njurestaurant.njutakeout.entity.company.System;

import java.util.List;

public interface SystemDataService {
    System saveSystem(System system);

    void deleteSystemById(int id);

    System findSystemById(int id);

    System findSystemByTitle(String title);

    List<System> findAll();
}
