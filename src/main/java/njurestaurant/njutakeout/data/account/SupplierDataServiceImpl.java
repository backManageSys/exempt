package njurestaurant.njutakeout.data.account;

import njurestaurant.njutakeout.data.dao.account.SupplierDao;
import njurestaurant.njutakeout.dataservice.account.SupplierDataService;
import njurestaurant.njutakeout.entity.account.Supplier;
import njurestaurant.njutakeout.publicdatas.account.SupplierState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierDataServiceImpl implements SupplierDataService {
    private final SupplierDao supplierDao;

    @Autowired
    public SupplierDataServiceImpl(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }

    /**
     *
     * @param supplier the supplier to be saved
     * @return
     */
    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierDao.save(supplier);
    }

    @Override
    public Supplier findSupplierById(int id) {
        Optional<Supplier> optionalSupplier = supplierDao.findById(id);
        if(optionalSupplier.isPresent()) {
            return optionalSupplier.get();
        } else {
            return null;
        }
    }

    @Override
    public void deleteSupplierById(int id) {
        supplierDao.deleteById(id);
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierDao.findAll();
    }

    @Override
    public List<Supplier> findSuppliersByState(String status) {
        return supplierDao.findSupplierByStatus(status);
    }

    @Override
    public List<Supplier> findSuppliersByLevel(int priority) {
        return supplierDao.findSuppliersByPriority(priority);
    }
}
