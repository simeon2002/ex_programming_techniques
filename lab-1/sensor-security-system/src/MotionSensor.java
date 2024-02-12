public class MotionSensor extends Sensor{
    private double distance;

    public MotionSensor(double distance, String type, String location, String manufacturer) {
        super(type, location, manufacturer);
        this.distance = distance;
    }

    public void alarmProcedure() {
        System.out.println("Alarm in " + type + " " + location +  "(" + manufacturer + ")");
        System.out.println("Contacting the police and siren is sounding");
    }

    @Override
    public String toString() {
        return super.toString() + "with an active radius of " + distance + "m";
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double d) {
        this.distance = d;
    }
}