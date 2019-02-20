package njurestaurant.njutakeout.dataservice.company;

import njurestaurant.njutakeout.entity.company.ReceiptCode;

import java.util.List;

public interface ReceiptCodeDataService {
    /**
     * save the receipt code
     *
     * @param receiptCode the code to be saved
     */
    ReceiptCode saveReceiptCode(ReceiptCode receiptCode);

    /**
     * find all codes
     *
     * @return
     */
    List<ReceiptCode> findAllReceipt();

    ReceiptCode findReceiptCodeById(int id);

    void deleteReceiptById(int id);


}
