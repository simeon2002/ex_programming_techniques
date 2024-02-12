import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.String;

public class ControlCenter{
    private String name;
    private Category category;
    ArrayList<Sensor> sensorList;

    public ControlCenter(String name, Category category) {
        this.name = name;
        this.category = category;
        this.sensorList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isPresent(Sensor s) {
        for (Sensor sensorFromList : sensorList) {
            if (s.equals(sensorFromList)) return true;
        }
        return false;
    }

    public void addSensor(Sensor s) {
        if (this.isPresent(s)) return;
        else sensorList.add(s);
    }

    public boolean removeSensor(Sensor sensorToRemove) {
        Iterator<Sensor> it = sensorList.iterator();
        while(it.hasNext()) {
            Sensor s = it.next();
            if (s.equals(sensorToRemove)) {
                it.remove();
                return true;
            }
        }
        System.out.println("Sensor hasn't been found in the list of sensors!");
        return false;
    }

    public int testSensor(Sensor s) {
        if (s.isActive() && this.isPresent(s)) {
            s.alarmProcedure();
            return sensorList.indexOf(s);
        }
        return -1;
    }

    public int testAllSensors() {
        int counter = 0;
        for (Sensor sensor : sensorList) {
            if (sensor.isActive()) {
                sensor.alarmProcedure();
                counter++;
            }
        }
        return counter;
    }

    public void printOverview() {
        System.out.println("Overview of all sensors @ " + name + " (" + this.category + ")");
        for (Sensor sensor : sensorList) {
            System.out.println(sensor.toString());
        }
    }

    public ArrayList<Sensor> getSensorList() {
        return sensorList;
    }


}