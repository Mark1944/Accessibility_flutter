
package com.example.sandbox_receiver;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "com.example.keyevents/receiveKeyEvent";
    private static final String TAG = "MainActivity";
    private static final String ACTION_CUSTOM_BROADCAST = "com.example.sandbox";
    private MethodChannel methodChannel;
    private KeyEventReceiver mReceiver;

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        methodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL);
        mReceiver = new KeyEventReceiver(methodChannel);

        // Register the receiver using the application context.
        IntentFilter filter = new IntentFilter(ACTION_CUSTOM_BROADCAST);
        getApplicationContext().registerReceiver(mReceiver, filter);

        methodChannel.setMethodCallHandler((call, result) -> {
            if (call.method.equals("receiveKeyEvent")) {
                String data = call.arguments();
                // Log.d(TAG, "Data received from KeyEventReceiver: " + data);
                        Log.i(TAG, "Data received: ddsdsdsd " + data);

                result.success("Received key event: " + data);
            
            } else {
                result.notImplemented();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getApplicationContext().unregisterReceiver(mReceiver);
    }

  
}
