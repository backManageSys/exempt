package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.account.PayRateList;
import njurestaurant.njutakeout.entity.company.PayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PayRateListDao extends JpaRepository<PayRateList, Integer> {
    PayRateList findById(int id);

    PayRateList findByUidAndPayTypeId(int uid, int payTypeId);

    List<PayRateList> findByUid(int uid);

    @Query(value = "select distinct uid from PayRateList")
    int[] findAllUid();
}
