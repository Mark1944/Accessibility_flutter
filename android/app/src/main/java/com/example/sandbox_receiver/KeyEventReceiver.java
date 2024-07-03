package com.example.sandbox_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public class KeyEventReceiver extends BroadcastReceiver {
    private static final String TAG = "KeyEventReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("keycode");
        Log.i("BR" ,"Data received:  " + data);
        //Toast.makeText(context, "Action: " + intent.getAction(), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "Data received: " + data, Toast.LENGTH_SHORT).show();
    }
}
