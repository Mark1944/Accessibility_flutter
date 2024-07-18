package com.example.sandbox;



// import android.accessibilityservice.AccessibilityService;
// import android.content.Intent;
// import android.os.Bundle;
// import android.util.Log;
// import android.view.accessibility.AccessibilityEvent;
// import android.view.accessibility.AccessibilityNodeInfo;

// import java.util.List;


// import android.accessibilityservice.AccessibilityServiceInfo;
// import android.widget.EditText;


// public class MyAccessibilityService extends AccessibilityService {

//     private static final String TAG = "AccessibilityService";

//     @Override
//     public void onCreate() {
//         super.onCreate();
//         Log.d(TAG, "Accessibility Service Created");
//     }

//     @Override
//     public void onServiceConnected() {
//         super.onServiceConnected();
//         AccessibilityServiceInfo info = new AccessibilityServiceInfo();
//         info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_VIEW_FOCUSED;
//         info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
//         info.notificationTimeout = 100;
//         setServiceInfo(info);
//     }

//     @Override
//     public int onStartCommand(Intent intent, int flags, int startId) {
//         if (intent != null) {
//             String username = intent.getStringExtra("username");
//             String password = intent.getStringExtra("password");
//             if (username != null && password != null) {
//                 sendTextToApp(username, password);
//             }
//         }
//         return START_STICKY;
//     }

//     @Override
//     public void onAccessibilityEvent(AccessibilityEvent event) {
//         Log.d(TAG, "Event: " + event.toString());
//         // Implement interaction logic here if needed
//     }   

//     @Override
//     public void onInterrupt() {
//         // Called when the accessibility service is interrupted
//         Log.d(TAG, "Accessibility Service Interrupted");
//     }

//     public void sendTextToApp(String username, String password) {


//                                     Log.d(TAG, "Accessibility Service Created send Here Here " + username + " " + password);


//         AccessibilityNodeInfo rootNode = getRootInActiveWindow();

//                 // String viewIdResourceName = rootNode.getViewIdResourceName();


//                                     Log.d(TAG, "Accessibility Service Created send Here" + username + " " + password);
//                                                                         Log.d(TAG, "Accessibility Service Created send Here " + rootNode);

//                                                                                                                                                 // Log.d(TAG, "Accessibility viewIdResourceName Created send Here " + viewIdResourceName);





//         if (rootNode != null) {

//             // Find the username field and set text
//             // List<AccessibilityNodeInfo> usernameFields = rootNode.findAccessibilityNodeInfosByViewId("com.example.sandbox_receiver:id/username_field");
//                         List<AccessibilityNodeInfo> usernameFields = rootNode.findAccessibilityNodeInfosByText("Username");


//                             Log.d(TAG, "Accessibility Service Created usernameFields" + usernameFields);

//             if (!usernameFields.isEmpty()) {
//                 AccessibilityNodeInfo usernameField = usernameFields.get(0);
//                 Bundle arguments = new Bundle();
//                 arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, username);
//                 usernameField.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
//             }

//             // Find the password field and set text
//             List<AccessibilityNodeInfo> passwordFields = rootNode.findAccessibilityNodeInfosByViewId("com.example.sandbox_receiver:id/password_field");
//             if (!passwordFields.isEmpty()) {
//                 AccessibilityNodeInfo passwordField = passwordFields.get(0);
//                 Bundle arguments = new Bundle();
//                 arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, password);
//                 passwordField.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
//             }
//         }
//     }

 
// }

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.content.Intent;

import java.util.List;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";
    private String username;
    private String password;


   @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_VIEW_FOCUSED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        info.notificationTimeout = 100;
        setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "Event: " + event.toString());
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            logAllNodes(rootNode);

            
        }
    }

    @Override
    public void onInterrupt() {
    }

     @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
                    AccessibilityNodeInfo rootNode = getRootInActiveWindow();

            username = intent.getStringExtra("username");   
            password = intent.getStringExtra("password");
            Log.d(TAG, "Received username: " + username + ", password: " + password);

                                    handleReceivedData(rootNode);

        }
        return START_STICKY;
    }


 private void handleReceivedData(AccessibilityNodeInfo rootNode) {

   

        sendTextToApp(rootNode, username, "Username");
        // sendTextToApp(rootNode, password, "Password");
        }
    
  

    private void logAllNodes(AccessibilityNodeInfo node) {
        if (node == null) return;
        Log.d(TAG, "Node: " + node.toString());
        for (int i = 0; i < node.getChildCount(); i++) {
            logAllNodes(node.getChild(i));
        }
    }


    private void sendTextToApp(AccessibilityNodeInfo rootNode,String text, String labelText) {
    findAndSetText(rootNode, text, labelText);
}

private void findAndSetText(AccessibilityNodeInfo node, String text, String fullViewId) {
    if (node == null) return;

            CharSequence contentDescription = node.getContentDescription();

                    Log.d(TAG, "contentDescription  contentDescription " + contentDescription);
                                        Log.d(TAG, "node  node " + node);


    String viewIdResourceName = node.getViewIdResourceName();
                    Log.d(TAG, "Text  viewIdResourceName " + viewIdResourceName);
                                        Log.d(TAG, "Text  fullViewId " + fullViewId);


    if (viewIdResourceName != null && viewIdResourceName.equals(fullViewId)) {
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
        node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        Log.d(TAG, "Text set in input field with ID: " + fullViewId);
        return;
    }
    
    else{
                Log.d(TAG, "Text  None");

    }

    for (int i = 0; i < node.getChildCount(); i++) {
        findAndSetText(node.getChild(i), text, fullViewId);
    }
}


    
}
