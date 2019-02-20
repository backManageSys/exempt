package njurestaurant.njutakeout.data.dao.account;

import njurestaurant.njutakeout.entity.account.Supplier;
import njurestaurant.njutakeout.publicdatas.account.SupplierState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierDao extends JpaRepository<Supplier, Integer> {
    List<Supplier> findSupplierByStatus(String status);
    Supplier findByUserId(int id);
    List<Supplier> findSuppliersByPriority(int priority);
}
