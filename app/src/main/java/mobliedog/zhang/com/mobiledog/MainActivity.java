package mobliedog.zhang.com.mobiledog;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    public static final String TAG = "SensorTest";


    private SensorManager sensorManager;
    private TextView tv;
    private PowerManager localPowerManager = null;// 电源管理对象
    private PowerManager.WakeLock localWakeLock = null;// 电源锁

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        tv = findViewById(R.id.tv);

        localPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        localWakeLock = this.localPowerManager.newWakeLock(32, "hahaha");// 第一个参数为电源锁级别，第二个是日志tag
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//TODO Auto -generated method stub
        float[] values = event.values;
        switch (event.sensor.getType()) {
            case Sensor.TYPE_PROXIMITY:
                tv.setText(values[0] + "");
                if (values[0] == 0.0) {// 贴近手机
                    System.out.println("hands up");
                    Log.d(TAG, "hands up in calling activity");
                    if (localWakeLock.isHeld()) {
                        return;
                    } else {
                        localWakeLock.acquire();// 申请设备电源锁
                    }
                } else {// 远离手机
                    System.out.println("hands moved");
                    Log.d(TAG, "hands moved in calling activity");
                    if (localWakeLock.isHeld()) {
                        return;
                    } else {
                        localWakeLock.setReferenceCounted(false);
                        localWakeLock.release(); // 释放设备电源锁
                    }
                    break;
                }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
