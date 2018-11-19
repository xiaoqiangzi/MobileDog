package mobliedog.zhang.com.mobiledog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import mobliedog.zhang.com.service.MobileDogService;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "SensorTest";


    private TextView tv;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);

//        localPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
//        localWakeLock = this.localPowerManager.newWakeLock(32, "hahaha");// 第一个参数为电源锁级别，第二个是日志tag
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent();
        intent.setClass(this, MobileDogService.class);
        startService(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
