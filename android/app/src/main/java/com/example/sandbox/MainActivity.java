package com.example.sandbox;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;
import android.widget.Toast;


public class MainActivity extends FlutterActivity {
  private static final String CHANNEL = "com.example.sandbox/accessibility";
   private static final String TAG = "MainActivity";

  @Override
  public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
    super.configureFlutterEngine(flutterEngine);
    new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
      .setMethodCallHandler((call, result) -> {
        if (call.method.equals("sendTextToAppB")) {
          String text = call.argument("text");
          Intent intent = new Intent(this, MyAccessibilityService.class);
          intent.putExtra("text", text);
          startService(intent);



    // Example: Log that text sending is initiated
    Log.d(TAG, "Sending text to Accessibility Service: " + text);
    
    // Example: Provide UI feedback (e.g., show a toast message)
    Toast.makeText(this, "Text sent to Accessibility Service"+ text , Toast.LENGTH_SHORT).show();
          result.success(null);
        } else {
          result.notImplemented();
        }
      });
  }
}

// public class MainActivity extends FlutterActivity {
//     private static final String CHANNEL = "com.example.keyevents/sendKeyEvent";
//     private static final String TAG = "MainActivity"; // Tag for logging

//     @Override
//     public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
//         super.configureFlutterEngine(flutterEngine);
//         new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
//                 .setMethodCallHandler(
//                         (call, result) -> {
//                             if (call.method.equals("sendKeyEvent")) {
//                                 try {
//                                     int keyCode = Integer.parseInt(call.argument("keyCode").toString());
//                                     sendKeyEvent(keyCode);
//                                     Log.d(TAG, "Success"); // Log success message
//                                     result.success("Success"); // Send success message back to Flutter
//                                 } catch (NumberFormatException e) {
//                                     Log.e(TAG, "Error parsing keyCode", e); // Log error
//                                     result.error("ERROR_PARSING_KEYCODE", "Failed to parse keyCode", null);
//                                 }
//                             } else {
//                                 result.notImplemented();
//                             }
//                         }
//                 );
//     }

//     private void sendKeyEvent(int keyCode) {
//         // Implement the logic to send a key event here
//         // This might involve sending a broadcast or using an accessibility service
//         Log.d(TAG, "sendKeyEvent called with keyCode: " + keyCode); // Log method call
//        /* Intent intent = new Intent();
//         intent.setAction("com.example.keyevents.KEY_EVENT");
//         intent.putExtra("keycode", keyCode);
//         sendBroadcast(intent, "my.app.PERMISSION");*/
//         Intent intent = new Intent();
//         intent.setAction("com.example.sandbox");
//         intent.putExtra("keycode", String.valueOf(keyCode));
//         sendBroadcast(intent, "com.example.sandbox");
//     }
// }
