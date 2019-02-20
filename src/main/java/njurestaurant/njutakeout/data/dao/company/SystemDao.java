package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.company.System;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemDao extends JpaRepository<System, Integer> {
    System findSystemByTitle(String title);
}
