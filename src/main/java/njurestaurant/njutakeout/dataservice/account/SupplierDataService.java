package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.account.Supplier;
import njurestaurant.njutakeout.publicdatas.account.SupplierState;

import java.util.List;

public interface SupplierDataService {

    /**
     * save the supplier
     * @param supplier the supplier to be saved
     * @return
     */
    Supplier saveSupplier(Supplier supplier);

    /**
     * find the supplied by id
     * @param id the id
     * @return
     */
    Supplier findSupplierById(int id);

    /**
     * delete the supplier by id
     *
     * @param id
     */
    void deleteSupplierById(int id);

    List<Supplier> getAllSuppliers();

    List<Supplier> findSuppliersByState(String status);

    List<Supplier> findSuppliersByLevel(int priority);

}
