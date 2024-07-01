package com.example.sandbox_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

public class KeyEventReceiver extends BroadcastReceiver {
    private static final String TAG = "KeyEventReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.requireNonNull(intent.getAction()).equals("com.example.keyevents.KEY_EVENT")) {
            int keyCode = intent.getIntExtra("keyCode", -1);
            if (keyCode != -1) {
                Log.d(TAG, "Received key event: " + keyCode);
                // Handle the received key event here
            }
        }
    }
}
