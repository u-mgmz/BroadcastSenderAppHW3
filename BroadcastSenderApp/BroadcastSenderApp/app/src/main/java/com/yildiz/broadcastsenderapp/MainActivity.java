package com.yildiz.broadcastsenderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView tv1;
    float x, y, z, light;
    private SensorManager mSensorManager;
    private Sensor sensoraccelerometer, sensorlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensoraccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorlight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(MainActivity.this, sensoraccelerometer, mSensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(MainActivity.this, sensorlight, mSensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];
        }
        else if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            light = sensorEvent.values[0];
        }


        if(light <= 0){
            if((Math.abs((z*z) + (x*x)) >= 81) && (Math.abs(x) >=6)){
                tv1.setText("Hareketsiz ve cepte");
                onBroadcastSend(3);
            }
            else if((Math.abs((y*y) + (x*x)) >= 81) && (Math.abs(x) <6)){
                tv1.setText("Hareketli ve cepte");
                onBroadcastSend(1);
            }
            else if((Math.abs((z*z)) >= 81)){
                tv1.setText("Hareketsiz masada");
                onBroadcastSend(2);
            }
            else{
                tv1.setText("Tespit yapilamadi");
                onBroadcastSend(4);
            }
        }
        else{
            if((Math.abs((z*z)) >= 81)){
                tv1.setText("Hareketsiz masada");
                onBroadcastSend(2);
            }
            else{
                tv1.setText("Tespit yapilamadi");
                onBroadcastSend(4);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void onBroadcastSend(int state){
        Intent intent = new Intent();
        intent.setAction("com.yildiz.myBroadcastMessage");
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("state", state);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(MainActivity.this, sensoraccelerometer);
        mSensorManager.unregisterListener(MainActivity.this, sensorlight);
    }
}