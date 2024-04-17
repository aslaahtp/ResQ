package commonClasses;

import java.io.Serializable;

public class ResModel implements Serializable {

    private String name;
    private String qty;
    private String contact;


    private boolean isSelected = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name =name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
    public String getcontact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact =contact;
    }






}
