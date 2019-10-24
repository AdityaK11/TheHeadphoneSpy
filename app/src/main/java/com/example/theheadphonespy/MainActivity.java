package com.example.theheadphonespy;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends Activity {
        private static final String TAG = "MainActivity";


        Intent mServiceIntent;
        private MyService mYourService;

        @Override protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            mYourService = new MyService();

            mServiceIntent = new Intent(this, mYourService.getClass());
            if (!isMyServiceRunning(mYourService.getClass())) {
                startService(mServiceIntent);
                Toast.makeText(MainActivity.this, "started", Toast.LENGTH_SHORT).show();
            }
        }

        private boolean isMyServiceRunning(Class<?> serviceClass) {
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    Log.i ("Service status", "Running");
                    return true;
                }
            }
            Log.i ("Service status", "Not running");
            return false;
        }

        @Override
        protected void onDestroy() {
            stopService(mServiceIntent);
            super.onDestroy();
        }
}
