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
    private Sensor rotationVecType;
    private SensorManager sensManager;
    private Handler serviceHandler;
    private static final int delaySensor = 100000;
    private boolean FirstTime = true;
    private boolean isSame = true;
    private float y;
    private float z;
    private float x;
    private float[] values = new float[3];
    private HandlerThread handlerThread;
    private boolean isListener = false;


    @Override
    public void onCreate() {
        super.onCreate();
        initSensor();
        initThread();
    }

    private void initSensor() {
        sensManager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
        rotationVecType = sensManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensManager.registerListener(this, rotationVecType, delaySensor);
    }

    private void initThread() {
        handlerThread = new HandlerThread(MemoryGameService.class.getSimpleName());
        handlerThread.start();
        serviceHandler = new Handler(handlerThread.getLooper());
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
        if (e.sensor != rotationVecType)
            return;

        float[] arrayRot = new float[9];
        float[] arrayNewRot = new float[9];
        SensorManager.getRotationMatrixFromVector(arrayRot, e.values);
        SensorManager.remapCoordinateSystem(arrayRot, SensorManager.AXIS_X, SensorManager.AXIS_Z, arrayNewRot);
        SensorManager.getOrientation(arrayNewRot, values);
        boolean rotated = true;
        if (FirstTime) {
            this.x = values[0];
            this.y = values[1];
            this.z = values[2];
            FirstTime = false;
        } else if (isChangeInDirection(x, values[0]) || isChangeInDirection(y, values[1]) || isChangeInDirection(z, values[2]))
            rotated = false;
        if (rotated != isSame) {
            isSame = rotated;
            Intent intent = new Intent(getString(R.string.change));
            intent.putExtra(getString(R.string.move), rotated);
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
            handlerThread.quit();
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
            if (msg != getString(R.string.listen_message) || isListener) return;
            Sensor theSensor = sensManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            if (theSensor == null) return;
            sensManager.registerListener(theService, theSensor, SensorManager.SENSOR_DELAY_UI, serviceHandler);
            isListener = true;
        }
    }
}
