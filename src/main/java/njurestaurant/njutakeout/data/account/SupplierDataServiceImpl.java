package njurestaurant.njutakeout.data.account;

import njurestaurant.njutakeout.data.dao.account.SupplierDao;
import njurestaurant.njutakeout.data.dao.account.UserDao;
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
    private final UserDao userDao;

    @Autowired
    public SupplierDataServiceImpl(SupplierDao supplierDao,UserDao userDao) {
        this.supplierDao = supplierDao;
        this.userDao=userDao;
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

        List<Supplier> suppliers=supplierDao.findAll();
        for (Supplier supplier:suppliers){
             supplier.setApplicant(userDao.findUserById(supplier.getApplicantId()).getUsername());
        }
        return  suppliers;
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
