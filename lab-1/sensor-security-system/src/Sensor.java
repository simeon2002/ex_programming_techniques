import java.util.Objects;

public abstract class Sensor {
    protected String type;
    protected String location;
    protected String manufacturer;
    protected boolean isActive;

    public Sensor(String type, String location, String manufacturer) {
        this.type = type;
        this.location = location;
        this.manufacturer = manufacturer;
        this.isActive = false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public abstract void alarmProcedure(); // abstract function
// TODO: fix such that the first part of every sensor in the Sensor class is defined.
    @Override
    public String toString () {
        String activeWord = this.isActive ? "active" : "inactive";
        return "Info of " + activeWord + " sensor (type = " + type + "), from " + manufacturer + " located at " + location;
    }
    public boolean equals(Sensor s) {
        if (this.type.equals(s.getType()) && this.location.equals(s.getLocation()) && this.manufacturer.equals(s.getManufacturer())) return true;
        return false;

    }
}