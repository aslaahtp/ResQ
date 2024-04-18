package commonClasses;

import java.io.Serializable;

public class SosModel implements Serializable {

    private String name;
    private String location;
    private String time;
    private String event_id;



    private boolean isSelected = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name =name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time =time;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String eventId) {
        this.event_id =eventId;
    }







}
