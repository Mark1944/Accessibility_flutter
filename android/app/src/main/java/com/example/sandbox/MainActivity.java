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
                        String username = call.argument("username");
                        String password = call.argument("password");


                        Intent intent = new Intent(this, MyAccessibilityService.class);
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);
                        startService(intent);

                        result.success("Data sent to Accessibility Service");
                    } 
                    else if (call.method.equals("tabPress")) {


                                                Boolean buttonPress = call.argument("buttonPress");


                         Log.d(TAG, "buttonPress buttonPress buttonPress" + buttonPress );


                        Intent intent = new Intent(this, ButtonAccessibilityService.class);
                        intent.putExtra("buttonPress", buttonPress);
                        startService(intent);

                        result.success("Button Pressed");
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
