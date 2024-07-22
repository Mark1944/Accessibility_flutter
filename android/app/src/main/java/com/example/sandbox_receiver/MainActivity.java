
package com.example.sandbox_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "com.example.sandbox_receiver/refresh";

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(refreshReceiver, new IntentFilter("com.example.sandbox.REFRESH_UI"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(refreshReceiver);
    }

    private BroadcastReceiver refreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            new MethodChannel(getFlutterEngine().getDartExecutor().getBinaryMessenger(), CHANNEL)
                    .invokeMethod("refreshUI", null);
        }
    };
}

