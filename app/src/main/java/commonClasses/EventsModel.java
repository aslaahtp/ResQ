package commonClasses;

import java.io.Serializable;

public class EventsModel implements Serializable {

    private String name;
    private String e_id;
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


    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id =e_id;
    }





}
