package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.company.ReceiptCodeDao;
import njurestaurant.njutakeout.dataservice.company.ReceiptCodeDataService;
import njurestaurant.njutakeout.entity.company.ReceiptCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceiptCodeDataServiceImpl implements ReceiptCodeDataService {
    private final ReceiptCodeDao receiptCodeDao;

    @Autowired
    public ReceiptCodeDataServiceImpl (ReceiptCodeDao receiptCodeDao) {
        this.receiptCodeDao = receiptCodeDao;
    }

    /**
     * save the receipt code
     * @param receiptCode the code to be saved
     */
    @Override
    public ReceiptCode saveReceiptCode(ReceiptCode receiptCode) {
        return receiptCodeDao.save(receiptCode);
    }

    /**
     * find all codes
     *
     * @return
     */
    @Override
    public List<ReceiptCode> findAllReceipt() {
        return receiptCodeDao.findAll();
    }

    @Override
    public ReceiptCode findReceiptCodeById(int id) {
        Optional<ReceiptCode> receiptCode = receiptCodeDao.findById(id);
        if(receiptCode.isPresent()) return receiptCode.get();
        else return null;
    }

    @Override
    public void deleteReceiptById(int id) {
        receiptCodeDao.deleteById(id);
    }
}
