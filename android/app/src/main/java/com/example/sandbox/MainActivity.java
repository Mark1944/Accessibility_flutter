package com.example.sandbox;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;
import android.util.Log;

public class MainActivity extends FlutterActivity {
  private static final String CHANNEL = "com.example.sandbox/accessibility";
      private static final String TAG = "AccessibilityS";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MethodChannel(getFlutterEngine().getDartExecutor().getBinaryMessenger(), CHANNEL)
            .setMethodCallHandler(
                (call, result) -> {
                    if (call.method.equals("sendText")) {
                        String keySent = call.argument("keySent");


                        Intent intent = new Intent(this, MyAccessibilityService.class);
                        intent.putExtra("keySent", keySent);
                        startService(intent);

                         Log.d(TAG, "Accessibility Service Createdsdd " + keySent);

                        result.success("Data sent to Accessibility Service");
                    } 
                  
                    
                    else {
                        result.notImplemented();
                    }
                }
            );
    }
}
  
  
//   public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
//     super.configureFlutterEngine(flutterEngine);
//     new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
//       .setMethodCallHandler((call, result) -> {
//         if (call.method.equals("sendTextToAppB")) {
//           String username = call.argument("username");
//                     String password = call.argument("password");

//           Intent intent = new Intent(this, MyAccessibilityService.class);
//           intent.putExtra("username", username);
//                     intent.putExtra("password", password);



//                           Log.d(TAG, "Accessibility Service Createdsdd" + username + " " + password);

//           startService(intent);
//           result.success(null);
//         } else {
//           result.notImplemented();
//         }
//       });
//   }
// }
