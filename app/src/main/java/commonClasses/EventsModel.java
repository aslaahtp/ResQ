package commonClasses;

import java.io.Serializable;

public class EventsModel implements Serializable {

    private String name;
    private String location;
    private String type;


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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type =type;
    }






}
