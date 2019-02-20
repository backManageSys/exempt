package njurestaurant.njutakeout.dataservice.app;

import njurestaurant.njutakeout.entity.app.Device;

import java.util.List;

public interface DeviceDataService {

    Device saveDevice(Device device);

    List<Device> findAll();

    Device findById(int id);

    void deleteById(int id);

    Device findByImei(String imei);

    Device findBySupplierIdAndImei(int id ,String imei);

    List<Device> findDevicesByImei(String imei);

    Device findByAlipayId(int id);

    List<Device> findDevicesBySupplierId(int id);
}
