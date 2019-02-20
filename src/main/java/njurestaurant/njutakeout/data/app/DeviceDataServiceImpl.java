package njurestaurant.njutakeout.data.app;

import njurestaurant.njutakeout.data.dao.app.DeviceDao;
import njurestaurant.njutakeout.dataservice.app.DeviceDataService;
import njurestaurant.njutakeout.entity.app.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceDataServiceImpl implements DeviceDataService {
    private final DeviceDao deviceDao;

    @Autowired
    public DeviceDataServiceImpl(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    @Override
    public Device saveDevice(Device device) {
        return deviceDao.save(device);
    }

    @Override
    public List<Device> findAll() {
        return deviceDao.findAll();
    }

    @Override
    public Device findById(int id) {
        Optional<Device> deviceOptional = deviceDao.findById(id);
        if(deviceOptional.isPresent())  return deviceOptional.get();
        else return null;
    }

    @Override
    public void deleteById(int id) {
        deviceDao.deleteById(id);
    }

    @Override
    public Device findByImei(String imei) {
        return deviceDao.findDeviceByImei(imei);
    }

    @Override
    public Device findBySupplierIdAndImei(int id, String imei) {
        return deviceDao.findDeviceBySupplierIdAndImei(id,imei);
    }

    @Override
    public List<Device> findDevicesByImei(String imei) {
        return deviceDao.findDevicesByImei(imei);
    }

    @Override
    public Device findByAlipayId(int id) {
        return deviceDao.findDeviceByAlipayId(id);
    }

    @Override
    public List<Device> findDevicesBySupplierId(int id) {
        return deviceDao.findDevicesBySupplierId(id);
    }
}
