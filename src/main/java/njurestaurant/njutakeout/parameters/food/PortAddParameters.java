package njurestaurant.njutakeout.parameters.food;

public class PortAddParameters {
    private String portName;

    public PortAddParameters() {
    }

    public PortAddParameters(String portName) {
        this.portName = portName;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }
}
