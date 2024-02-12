public class COSensor extends Sensor {
    private double minConcentration;

    public COSensor(double minConcentration, String type, String location, String manufacturer) {
        super(type, location, manufacturer);
        this.minConcentration = minConcentration;
    }

    public void alarmProcedure() {
        System.out.println("Alarm in " + type + " " + location +  "(" + manufacturer + ")");
        System.out.println("Windows are being closed and siren is sounding");
    }

    @Override
    public String toString() {
        return super.toString() + " with minimum concentration = " + minConcentration;
    }

    public double getMinConcentration() {
        return minConcentration;
    }

    public void setMinConcentration(double minConcentration) {
        this.minConcentration = minConcentration;
    }
}