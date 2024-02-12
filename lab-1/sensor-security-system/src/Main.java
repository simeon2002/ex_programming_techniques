import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ControlCenter center = new ControlCenter("Campus Groep T", Category.MEDIUM);
        // motion sensor
        Sensor s1 = new MotionSensor(0.3, "motion sensor", "kitchen", "STMicroelectronics");
        // duplicate
        Sensor s2 = new MotionSensor(0.3, "motion sensor", "kitchen", "STMicroelectronics");
        // other
        Sensor s3 = new MotionSensor(5.4, "motion sensor", "garden", "STMicroelectronics");

        // CO sensor
        Sensor s4 = new COSensor(3.5, "CO sensor", "living room", "STMicroelectronics");
        // duplicate
        Sensor s5 = new COSensor(3.5, "CO sensor", "living room", "STMicroelectronics");
        Sensor s6 = new COSensor(4, "CO sensor", "garage", "STMicroelectronics");

        // smoke sensor
        Sensor s7 = new SmokeSensor("smoke sensor", "garage", "STMicrolectronics", true, false);
        // duplicate
        Sensor s8 = new SmokeSensor("smoke sensor", "garage", "STMicrolectronics", true, false);
        // almost similar to s7 with heatdetection as well!
        Sensor s9 = new SmokeSensor("smoke sensor", "garage", "NXP Semiconductors", true, true);
        // other
        Sensor s10 = new SmokeSensor("smoke sensor", "toilet", "NXP Semiconductors", true, false);

        center.addSensor(s1);
        center.addSensor(s2); // duplicate
        center.addSensor(s3);
        center.addSensor(s4);
        center.addSensor(s5); // duplicate
        center.addSensor(s6);
        center.addSensor(s7);
        center.addSensor(s8);// duplicate
        center.addSensor(s9);
        center.addSensor(s10);

        // displaying overview
        System.out.println("Printing full overview");
        center.printOverview();
        System.out.println("\n\n");
        // turning 4 out of 3 to active

        s1.setActive(true);
        s4.setActive(true);
        s6.setActive(true);
        s7.setActive(true);

        // displaying overview again
        System.out.println("Printing full overview");
        center.printOverview();
        System.out.println("\n\n");

        // testing sensors s1, s3, s6, and s9 + 2 dummy sensors
        System.out.println("index = " +  center.testSensor(s1)); // active
        System.out.println("index = " + center.testSensor(s3)); // NOT active
        System.out.println("index = " + center.testSensor(s6)); // active
        System.out.println("index = " + center.testSensor(s9)); // NOT active
        System.out.println("index = " + center.testSensor(new COSensor(5,"test", "garden", "no-one"))); // NOT active
        System.out.println("index = " + center.testSensor(s8)); // NOT active
        System.out.println("\n");


        // testing ALL sensors to see which are active (1, 4, 6, 7 should be active)
        System.out.println("count of active sensors: " + center.testAllSensors());
        System.out.println();
        System.out.println();

        // testing the removal of a sensor
        ArrayList<Sensor> sensorList = center.getSensorList();
        int countBefore = 0, countAfter = 0;
        countBefore = sensorList.size();
        System.out.println("Sensor removed = " + center.removeSensor(s1));
        countAfter = sensorList.size();
        System.out.println("Sensor actually removed == " + (countAfter == countBefore - 1));
        System.out.println();
        System.out.println();

        // testing equal method of sensor.
        System.out.println(s1.equals(s2));
    }

}
