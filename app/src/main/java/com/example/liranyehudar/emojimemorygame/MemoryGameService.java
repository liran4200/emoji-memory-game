package com.example.liranyehudar.emojimemorygame;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class MemoryGameService extends Service implements SensorEventListener {
    public SensorBind binder = new SensorBind();
    private Sensor rotationSens;
    private SensorManager sensManager;
    private Handler serviceHandler;
    private static final int delayForSensor = 100000;
    private boolean isFirstTime = true;
    private boolean isSameRotation = true;
    private float y;
    private float z;
    private float x;
    private float[] values = new float[3];
    private HandlerThread threadSens;
    private boolean listenerBool = false;


    @Override
    public void onCreate() {
        super.onCreate();
        initSensor();
        initThread();
    }

    private void initSensor() {
        sensManager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
        rotationSens = sensManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensManager.registerListener(this, rotationSens, delayForSensor);
    }

    private void initThread() {
        threadSens = new HandlerThread(MemoryGameService.class.getSimpleName());
        threadSens.start();
        serviceHandler = new Handler(threadSens.getLooper());
    }


    @Override
    public boolean onUnbind(Intent intent) {
        try {
            sensManager.unregisterListener(this);
            sensManager = null;
        } catch (NullPointerException e) {
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        if (e.sensor != rotationSens)
            return;

        float[] rotationArr = new float[9];
        float[] newRotationArr = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationArr, e.values);
        SensorManager.remapCoordinateSystem(rotationArr, SensorManager.AXIS_X, SensorManager.AXIS_Z, newRotationArr);
        SensorManager.getOrientation(newRotationArr, values);
        boolean isRotated = true;
        if (isFirstTime) {
            this.x = values[0];
            this.y = values[1];
            this.z = values[2];
            isFirstTime = false;
        } else if (isChangeInDirection(x, values[0]) || isChangeInDirection(y, values[1]) || isChangeInDirection(z, values[2]))
            isRotated = false;
        if (isRotated != isSameRotation) {
            isSameRotation = isRotated;
            Intent intent = new Intent(getString(R.string.change_str));
            intent.putExtra(getString(R.string.right_str), isRotated);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    private boolean isChangeInDirection(float oldDirection, float newDirection) {
        if (Math.abs(oldDirection - newDirection) > 0.5)
            return true;
        return false;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            sensManager.unregisterListener(this);
        } catch (Exception e) {

        }
        try {
            threadSens.quit();
        } catch (Exception e) {
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        binder.theService = this;
        return binder;
    }

    class SensorBind extends Binder {
        private MemoryGameService theService;

        void notifyService(String msg) {
            if (msg != getString(R.string.listen_message) || listenerBool) return;
            Sensor s = sensManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            if (s == null) return;
            sensManager.registerListener(theService, s, SensorManager.SENSOR_DELAY_UI, serviceHandler);
            listenerBool = true;
        }
    }
}
