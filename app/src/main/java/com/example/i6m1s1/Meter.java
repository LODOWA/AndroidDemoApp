package com.example.i6m1s1;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Meter extends AppCompatActivity {

    private TextView txt;
   // private SensorManager sensorManager;
  //  private Sensor pressureSensor;
    private SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values = sensorEvent.values;
            txt.setText(String.format("%.3f mbar", values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt);
       //sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
     //   pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }
/*
    @Override
    protected void onResume() {
       // super.onResume();
       // sensorManager.registerListener(sensorEventListener, pressureSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        //super.onPause();
      //  sensorManager.unregisterListener(sensorEventListener);
    }*/
}