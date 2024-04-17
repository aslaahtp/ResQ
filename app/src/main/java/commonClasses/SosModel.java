package commonClasses;

import java.io.Serializable;

public class SosModel implements Serializable {

    private String name;
    private String location;
    private String time;


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






}
