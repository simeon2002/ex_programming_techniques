public class SmokeSensor extends Sensor {
    private boolean smokeDetection;
    private boolean heatDetection;

    public SmokeSensor(String type, String location, String manufacturer, boolean smokeDetection, boolean heatDetection) {
        super(type, location, manufacturer);
        this.smokeDetection = smokeDetection;
        this.heatDetection = heatDetection;
    }

    public void alarmProcedure() {
        System.out.println("Alarm in " + type + " " + location +  "(" + manufacturer + ")");
        System.out.println("Preventing a potential fire from spreading and siren is sounding");
    }

    @Override
    public String toString() {
        return super.toString() + " smokeOnly = " + this.isSmokeOnly();
    }

    public boolean isSmokeOnly() {
        return this.smokeDetection && !this.heatDetection;
    }

    public boolean isSmokeDetection() {
        return smokeDetection;
    }

    public void setSmokeDetection(boolean smokeDetection) {
        this.smokeDetection = smokeDetection;
    }

    public boolean isHeatDetection() {
        return heatDetection;
    }

    public void setHeatDetection(boolean heatDetection) {
        this.heatDetection = heatDetection;
    }
}