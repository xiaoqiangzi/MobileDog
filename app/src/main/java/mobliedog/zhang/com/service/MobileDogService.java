package mobliedog.zhang.com.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MobileDogService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    public static final String TAG = "MobileDogService";

    public MobileDogService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_UI);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        switch (event.sensor.getType()) {
            case Sensor.TYPE_PROXIMITY:
                if (values[0] == 0.0) {// 贴近手机
                    System.out.println("hands up");
                    Log.d(TAG, "hands up in calling activity");
                    Toast.makeText(this, "贴近手机", Toast.LENGTH_SHORT).show();
//                    if (localWakeLock.isHeld()) {
//                        return;
//                    } else {
//                        localWakeLock.acquire();// 申请设备电源锁
//                    }
                } else {// 远离手机
                    System.out.println("hands moved");
                    Log.d(TAG, "hands moved in calling activity");
                    Toast.makeText(this, "远离手机", Toast.LENGTH_SHORT).show();
//                    if (localWakeLock.isHeld()) {
//                        return;
//                    } else {
//                        localWakeLock.setReferenceCounted(false);
//                        localWakeLock.release(); // 释放设备电源锁
//                    }
                    break;
                }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}
