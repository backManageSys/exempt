package njurestaurant.njutakeout.data.port;

import njurestaurant.njutakeout.data.dao.port.PortDao;
import njurestaurant.njutakeout.dataservice.port.PortDataService;
import njurestaurant.njutakeout.entity.port.Port;
import njurestaurant.njutakeout.exception.PortDoesNotExistException;
import njurestaurant.njutakeout.publicdatas.port.PortState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PortDataServiceImpl implements PortDataService {
    private final PortDao portDao;

    @Autowired
    public PortDataServiceImpl(PortDao portDao) {
        this.portDao = portDao;
    }

    /**
     * get all ports
     *
     * @return
     */
    @Override
    public List<Port> getAllPorts() {
        return portDao.findAll();
    }

    /**
     * get port by id
     *
     * @param portId
     * @return
     */
    @Override
    public Port getPortById(int portId) throws PortDoesNotExistException {
        Optional<Port> optionalPort = portDao.findById(portId);
        if (optionalPort.isPresent()) {
            return optionalPort.get();
        } else {
            throw new PortDoesNotExistException();
        }
    }

    /**
     * get port by port name
     *
     * @param portName
     * @return
     */
    @Override
    public Port getPortByPortName(String portName) throws PortDoesNotExistException {
        Port port = portDao.findPortByName(portName);
        if (port != null) {
            return port;
        } else {
            throw new PortDoesNotExistException();
        }
    }

    /**
     * add port
     *
     * @param portName
     * @return
     */
    @Override
    public Port addPort(String portName) {
        return portDao.save(new Port(portName, PortState.ACTIVE, new ArrayList<>()));
    }

    /**
     * delete a port
     *
     * @param portId
     * @return
     */
    @Override
    public void deletePort(int portId) throws PortDoesNotExistException {
        Optional<Port> optionalPort=portDao.findById(portId);
        if(optionalPort.isPresent()){
            Port port=optionalPort.get();
            port.setPortState(PortState.INACTIVE);
            portDao.save(port);
        }else{
            throw new PortDoesNotExistException();
        }
    }
}
