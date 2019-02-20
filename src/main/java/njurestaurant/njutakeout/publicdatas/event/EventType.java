package njurestaurant.njutakeout.publicdatas.event;

import njurestaurant.njutakeout.parameters.event.*;

public enum EventType {
    FullSubtraction("FullSubtraction", FullSubtractionEventParameters.class),
    FirstOrder("FirstOrder", FirstOrderEventParameters.class),
    ItemSubtraction("ItemSubtraction", ItemSubtractionEventParameters.class),
    ItemSubtractionOnce("ItemSubtractionOnce", ItemSubtractionOnceEventParameters.class);

    private final String name;
    private final Class<? extends EventAddParameters> eventClass;

    EventType(String name, Class<? extends EventAddParameters> eventClass) {
        this.name = name;
        this.eventClass = eventClass;
    }

    public String getName() {
        return name;
    }

    public Class<? extends EventAddParameters> getEventClass() {
        return eventClass;
    }
}
