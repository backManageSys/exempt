package njurestaurant.njutakeout.dataservice.port;

import njurestaurant.njutakeout.entity.port.Port;
import njurestaurant.njutakeout.exception.PortDoesNotExistException;

import java.util.List;

public interface PortDataService {
    /**
     * get all ports
     *
     * @return
     */
    List<Port> getAllPorts();

    /**
     * get port by id
     *
     * @param portId
     * @return
     */
    Port getPortById(int portId) throws PortDoesNotExistException;

    /**
     * get port by port name
     *
     * @param portName
     * @return
     */
    Port getPortByPortName(String portName) throws PortDoesNotExistException;

    /**
     * add port
     *
     * @param portName
     * @return
     */
    Port addPort(String portName);

    /**
     * delete a port
     *
     * @param portId
     * @return
     */
    void deletePort(int portId) throws PortDoesNotExistException;
}
