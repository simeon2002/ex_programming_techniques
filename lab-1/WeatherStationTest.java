import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WeatherStationTest
{
    /**
     * Tests a measuring instrument
     */
    @Test
    public void test1()
    {
        //At creation time, each measuring instrument takes an initial value
        MeasuringInstrument instrument = new MeasuringInstrument( 21.2f );

        //Make sure both min and max value are equal to the initial measurement
        assertEquals( 21.2f, instrument.getValue(), 0.001f );
        assertEquals( 21.2f, instrument.getMinValue(), 0.001f );
        assertEquals( 21.2f, instrument.getMaxValue(), 0.001f );

        //New measurement, update min and max accordingly
        instrument.newMeasurement( 19.0f );
        assertEquals( 19.0f, instrument.getValue(), 0.001f );
        assertEquals( 19.0f, instrument.getMinValue(), 0.001f );
        assertEquals( 21.2f, instrument.getMaxValue(), 0.001f );

        instrument.newMeasurement( 24.5f );
        assertEquals( 24.5f, instrument.getValue(), 0.001f );
        assertEquals( 19.0f, instrument.getMinValue(), 0.001f );
        assertEquals( 24.5f, instrument.getMaxValue(), 0.001f );

        //Test the toString method.
        assertEquals( "current = 24.5, min = 19.0, max = 24.5", instrument.toString() );

        //When resetting, the current value is assigned as the min and max value
        instrument.newMeasurement( 22.3f );
        instrument.reset();
        assertEquals( 22.3f, instrument.getValue(), 0.001f );
        assertEquals( 22.3f, instrument.getMinValue(), 0.001f );
        assertEquals( 22.3f, instrument.getMaxValue(), 0.001f );
    }

    /**
     * Tests the thermometer
     */
    @Test
    public void test2()
    {
        //Tests constructor of thermometer. The only legal values for unit are 'C' and 'F', different values get converted to 'C'
        Thermometer thermometer = new Thermometer( 21.2f, 'C' );
        Thermometer thermometer2 = new Thermometer( 21.2f, 'F' );
        Thermometer thermometer3 = new Thermometer( 21.2f, 'D' );
        assertEquals( 'C', thermometer.getUnit(), 0.001f );
        assertEquals( 'F', thermometer2.getUnit(), 0.001f );
        assertEquals( 'C', thermometer3.getUnit(), 0.001f );

        //Make sure both min and max value are equal to the initial measurement
        assertEquals( 21.2f, thermometer.getValue(), 0.001f );
        assertEquals( 21.2f, thermometer.getMinValue(), 0.001f );
        assertEquals( 21.2f, thermometer.getMaxValue(), 0.001f );

        //New measurement, update min and max accordingly
        thermometer.newMeasurement( 19.0f );
        assertEquals( 19.0f, thermometer.getValue(), 0.001f );
        assertEquals( 19.0f, thermometer.getMinValue(), 0.001f );
        assertEquals( 21.2f, thermometer.getMaxValue(), 0.001f );

        thermometer.newMeasurement( 24.5f );
        assertEquals( 24.5f, thermometer.getValue(), 0.001f );
        assertEquals( 19.0f, thermometer.getMinValue(), 0.001f );
        assertEquals( 24.5f, thermometer.getMaxValue(), 0.001f );

        //Test the toString method. Note the inlcusion of the unit in this case
        assertEquals( "current = 24.5°C, min = 19.0°C, max = 24.5°C", thermometer.toString() );

        //When resetting, the current value is assigned as the min and max value
        thermometer.newMeasurement( 22.3f );
        thermometer.reset();
        assertEquals( 22.3f, thermometer.getValue(), 0.001f );
        assertEquals( 22.3f, thermometer.getMinValue(), 0.001f );
        assertEquals( 22.3f, thermometer.getMaxValue(), 0.001f );
    }

    /**
     * Tests the barometer
     */
    @Test
    public void test3()
    {
        //Tests the constructor of barometer
        Barometer barometer = new Barometer( 1023 );

        //Tests the divergence from the norm value of 1013 millibar
        assertEquals( 10, barometer.divergenceFromNorm(), 0.001f );

        //Make sure both min and max value are equal to the initial measurement
        assertEquals( 1023, barometer.getValue(), 0.001f );
        assertEquals( 1023, barometer.getMinValue(), 0.001f );
        assertEquals( 1023, barometer.getMaxValue(), 0.001f );

        //New measurement, update min and max accordingly
        barometer.newMeasurement( 1025.3f );
        assertEquals( 1025.3f, barometer.getValue(), 0.001f );
        assertEquals( 1023, barometer.getMinValue(), 0.001f );
        assertEquals( 1025.3f, barometer.getMaxValue(), 0.001f );
        assertEquals( 12.3f, barometer.divergenceFromNorm(), 0.001f );

        barometer.newMeasurement( 1019.6f );
        assertEquals( 1019.6f, barometer.getValue(), 0.001f );
        assertEquals( 1019.6f, barometer.getMinValue(), 0.001f );
        assertEquals( 1025.3f, barometer.getMaxValue(), 0.001f );

        //Test the toString method.
        assertEquals( "current = 1019.6, min = 1019.6, max = 1025.3", barometer.toString() );

        //When resetting, the current value is assigned as the min and max value
        barometer.reset();
        assertEquals( 1019.6f, barometer.getValue(), 0.001f );
        assertEquals( 1019.6f, barometer.getMinValue(), 0.001f );
        assertEquals( 1019.6f, barometer.getMaxValue(), 0.001f );
    }

    /**
     * Tests the pluviometer
     */
    @Test
    public void test4()
    {
        //Tests the constructor of pluviometer.
        Pluviometer pluviometer = new Pluviometer( 0.2f );

        //Make sure min value, max value and total measurement are equal to the initial measurement
        assertEquals( 0.2f, pluviometer.getValue(), 0.001f );
        assertEquals( 0.2f, pluviometer.getMinValue(), 0.001f );
        assertEquals( 0.2f, pluviometer.getMaxValue(), 0.001f );
        assertEquals( 0.2f, pluviometer.getTotalRainMeasured(), 0.001f );

        //New measurement, update min, max and total rain accordingly
        pluviometer.newMeasurement( 1.15f );
        assertEquals( 1.15f, pluviometer.getValue(), 0.001f );
        assertEquals( 0.2f, pluviometer.getMinValue(), 0.001f );
        assertEquals( 1.15f, pluviometer.getMaxValue(), 0.001f );
        assertEquals( 1.35f, pluviometer.getTotalRainMeasured(), 0.001f );

        pluviometer.newMeasurement( 0.56f );
        assertEquals( 0.56f, pluviometer.getValue(), 0.001f );
        assertEquals( 0.2f, pluviometer.getMinValue(), 0.001f );
        assertEquals( 1.15f, pluviometer.getMaxValue(), 0.001f );
        assertEquals( 1.91f, pluviometer.getTotalRainMeasured(), 0.001f );

        //Test the toString method.
        assertEquals( "current = 0.56, min = 0.2, max = 1.15", pluviometer.toString() );

        //When resetting, the current value is assigned as the min and max value. Note this should NOT affect total rain measured!
        pluviometer.reset();
        assertEquals( 0.56f, pluviometer.getValue(), 0.001f );
        assertEquals( 0.56f, pluviometer.getMinValue(), 0.001f );
        assertEquals( 0.56f, pluviometer.getMaxValue(), 0.001f );
        assertEquals( 1.91f, pluviometer.getTotalRainMeasured(), 0.001f );
    }

    /**
     * Tests the weather station: new measurement
     */
    @Test
    public void test5()
    {
        //Create weather station and instruments
        WeatherStation station = new WeatherStation();
        Thermometer thermometer1 = new Thermometer( 11.2f, 'C' );
        Thermometer thermometer2 = new Thermometer( 52.16f, 'F' );
        Barometer barometer = new Barometer( 1014.1f );
        Pluviometer pluviometer = new Pluviometer( 0.0f );

        //Add instruments to weather station
        station.addMeasuringInstrument( thermometer1 );
        station.addMeasuringInstrument( thermometer2 );
        station.addMeasuringInstrument( barometer );
        station.addMeasuringInstrument( pluviometer );

        //Test new measurement
        station.newMeasurement( 12.4f, 54.32f, 1014.18f, 0.19f );

        assertEquals( 12.4f, thermometer1.getValue(), 0.001f );
        assertEquals( 11.2f, thermometer1.getMinValue(), 0.001f );
        assertEquals( 12.4f, thermometer1.getMaxValue(), 0.001f );

        assertEquals( 54.32f, thermometer2.getValue(), 0.001f );
        assertEquals( 52.16f, thermometer2.getMinValue(), 0.001f );
        assertEquals( 54.32f, thermometer2.getMaxValue(), 0.001f );

        assertEquals( 1014.18f, barometer.getValue(), 0.001f );
        assertEquals( 1014.1f, barometer.getMinValue(), 0.001f );
        assertEquals( 1014.18f, barometer.getMaxValue(), 0.001f );

        assertEquals( 0.19f, pluviometer.getValue(), 0.001f );
        assertEquals( 0.0f, pluviometer.getMinValue(), 0.001f );
        assertEquals( 0.19f, pluviometer.getMaxValue(), 0.001f );
        assertEquals( 0.19f, pluviometer.getTotalRainMeasured(), 0.001f );
    }

    /**
     * Tests the weather station: new measurement with incorrect number of values. Nothing should happen in this case.
     */
    @Test
    public void test6()
    {
        //Create weather station and instruments
        WeatherStation station = new WeatherStation();
        Thermometer thermometer1 = new Thermometer( 11.2f, 'C' );
        Thermometer thermometer2 = new Thermometer( 52.16f, 'F' );
        Barometer barometer = new Barometer( 1014.1f );
        Pluviometer pluviometer = new Pluviometer( 0.0f );

        //Add instruments to weather station
        station.addMeasuringInstrument( thermometer1 );
        station.addMeasuringInstrument( thermometer2 );
        station.addMeasuringInstrument( barometer );
        station.addMeasuringInstrument( pluviometer );

        //Correct measurement
        station.newMeasurement( 12.4f, 54.32f, 1014.18f, 0.19f );
        //Incorrect measurements (these should be ignored)
        station.newMeasurement( 13.6f, 56.48f, 1014.09f );
        station.newMeasurement( 13.6f, 56.48f, 1014.09f, 0.22f, 10.5f );

        assertEquals( 12.4f, thermometer1.getValue(), 0.001f );
        assertEquals( 11.2f, thermometer1.getMinValue(), 0.001f );
        assertEquals( 12.4f, thermometer1.getMaxValue(), 0.001f );

        assertEquals( 54.32f, thermometer2.getValue(), 0.001f );
        assertEquals( 52.16f, thermometer2.getMinValue(), 0.001f );
        assertEquals( 54.32f, thermometer2.getMaxValue(), 0.001f );

        assertEquals( 1014.18f, barometer.getValue(), 0.001f );
        assertEquals( 1014.1f, barometer.getMinValue(), 0.001f );
        assertEquals( 1014.18f, barometer.getMaxValue(), 0.001f );

        assertEquals( 0.19f, pluviometer.getValue(), 0.001f );
        assertEquals( 0.0f, pluviometer.getMinValue(), 0.001f );
        assertEquals( 0.19f, pluviometer.getMaxValue(), 0.001f );
        assertEquals( 0.19f, pluviometer.getTotalRainMeasured(), 0.001f );
    }

    /**
     * Tests the weather station: reset
     */
    @Test
    public void test7()
    {
        //Create weather station and instruments
        WeatherStation station = new WeatherStation();
        Thermometer thermometer1 = new Thermometer( 11.2f, 'C' );
        Thermometer thermometer2 = new Thermometer( 52.16f, 'F' );
        Barometer barometer = new Barometer( 1014.1f );
        Pluviometer pluviometer = new Pluviometer( 0.2f );

        //Add instruments to weather station
        station.addMeasuringInstrument( thermometer1 );
        station.addMeasuringInstrument( thermometer2 );
        station.addMeasuringInstrument( barometer );
        station.addMeasuringInstrument( pluviometer );

        //New measurement and subsequent reset
        station.newMeasurement( 12.4f, 54.32f, 1014.18f, 0.19f );
        station.reset();

        assertEquals( 12.4f, thermometer1.getValue(), 0.001f );
        assertEquals( 12.4f, thermometer1.getMinValue(), 0.001f );
        assertEquals( 12.4f, thermometer1.getMaxValue(), 0.001f );

        assertEquals( 54.32f, thermometer2.getValue(), 0.001f );
        assertEquals( 54.32f, thermometer2.getMinValue(), 0.001f );
        assertEquals( 54.32f, thermometer2.getMaxValue(), 0.001f );

        assertEquals( 1014.18f, barometer.getValue(), 0.001f );
        assertEquals( 1014.18f, barometer.getMinValue(), 0.001f );
        assertEquals( 1014.18f, barometer.getMaxValue(), 0.001f );

        assertEquals( 0.19f, pluviometer.getValue(), 0.001f );
        assertEquals( 0.19f, pluviometer.getMinValue(), 0.001f );
        assertEquals( 0.19f, pluviometer.getMaxValue(), 0.001f );
        assertEquals( 0.39f, pluviometer.getTotalRainMeasured(), 0.001f );
    }

    /**
     * Tests the weather station: print
     */
    @Test
    public void test8()
    {
        //Create weather station and instruments
        WeatherStation station = new WeatherStation();
        Thermometer thermometer1 = new Thermometer( 11.2f, 'C' );
        Thermometer thermometer2 = new Thermometer( 52.16f, 'F' );
        Barometer barometer = new Barometer( 1014.1f );
        Pluviometer pluviometer = new Pluviometer( 0.2f );

        //Add instruments to weather station
        station.addMeasuringInstrument( thermometer1 );
        station.addMeasuringInstrument( thermometer2 );
        station.addMeasuringInstrument( barometer );
        station.addMeasuringInstrument( pluviometer );

        //New measurement
        station.newMeasurement( 12.4f, 54.32f, 1014.18f, 0.19f );

        //Test print
        PrintStream defaultSO = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        String result = null;
        try
        {
            //The lines in the assertEquals statements should be printed one by one, each on its own line
            station.print();
            BufferedReader br = new BufferedReader(new StringReader(baos.toString()));
            result = br.readLine();
            assertEquals( "current = 12.4°C, min = 11.2°C, max = 12.4°C", result );
            result = br.readLine();
            assertEquals( "current = 54.32°F, min = 52.16°F, max = 54.32°F", result );
            result = br.readLine();
            assertEquals( "current = 1014.18, min = 1014.1, max = 1014.18", result );
            result = br.readLine();
            assertEquals( "current = 0.19, min = 0.19, max = 0.2", result );
        }
        catch(Exception e)
        {
            System.setOut(defaultSO);
            System.out.println("Error while redirection System.out");
        }
        System.setOut(defaultSO);
    }
}
