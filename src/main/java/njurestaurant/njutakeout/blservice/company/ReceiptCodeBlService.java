package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.ReceiptCode;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.response.company.ReceiptCodeAddResponse;
import njurestaurant.njutakeout.response.company.ReceiptCodeLoadResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReceiptCodeBlService {

    /**
     * add a new receipt code
     *
     * @param receiptCode
     * @return
     */
    ReceiptCode addReceiptCode(ReceiptCode receiptCode);

    /**
     * load all receipt code
     *
     * @return
     */
    List<ReceiptCodeLoadResponse> loadReceiptCodes();

    void delReceiptCode(int id);

    ReceiptCodeLoadResponse findReceiptCodeById(int id);
}
