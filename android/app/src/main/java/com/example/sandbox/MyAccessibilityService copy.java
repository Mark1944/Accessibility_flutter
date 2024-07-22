// package com.example.sandbox;


// import android.accessibilityservice.AccessibilityService;
// import android.accessibilityservice.AccessibilityServiceInfo;
// import android.content.Intent;
// import android.os.Bundle;
// import android.util.Log;
// import android.view.accessibility.AccessibilityEvent;
// import android.view.accessibility.AccessibilityNodeInfo;

// public class MyAccessibilityService extends AccessibilityService {
//     private static final String TAG = "MyAccessibilityService";
//     private String username;
//     private String password;

//         private String targetPackage = "com.example.sandbox_receiver"; 


//     @Override
//     public void onServiceConnected() {
//         super.onServiceConnected();
//         AccessibilityServiceInfo info = new AccessibilityServiceInfo();
//         info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_VIEW_FOCUSED | AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;
//         info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
//         setServiceInfo(info);
//         Log.d(TAG, "Accessibility Service Connected");
//     }

//     @Override
//     public int onStartCommand(Intent intent, int flags, int startId) {
//         if (intent != null) {
//             username = intent.getStringExtra("username");
//             password = intent.getStringExtra("password");
//             Log.d(TAG, "Received username: " + username + ", password: " + password);
//         }
//         return START_STICKY;
//     }

//     @Override
//     public void onAccessibilityEvent(AccessibilityEvent event) {
//         Log.d(TAG, "Event: " + event.toString());

//           // Check if the event is from the target package
//         if (event.getPackageName() != null && event.getPackageName().toString().equals(targetPackage)) {
           
//         AccessibilityNodeInfo rootNode = getRootInActiveWindow();
//         if (rootNode != null) {
//             AccessibilityNodeInfo focusedNode = findFocusedNode(rootNode);
//             if (focusedNode != null) {
//                 Log.d(TAG, "Focused node: " + focusedNode.toString());
//                 if (username != null) {
//                     setTextInFocusedNode(focusedNode, username);
//                     // simulateFocusChange(focusedNode);
//                 } 
                
//                 // else if (password != null) {
//                 //     setTextInFocusedNode(focusedNode, password);
//                 //     simulateFocusChange(focusedNode);
//                 // }
//             } else {
//                 Log.d(TAG, "No focused node found");
//             }
        
//         } else {
//             Log.d(TAG, "Root node is null");
//         }

//         }else {
//             Log.d(TAG, "Event from non-target package: " + (event.getPackageName() != null ? event.getPackageName().toString() : "null"));
//         }
//     }

//     @Override
//     public void onInterrupt() {
//     }

//     private AccessibilityNodeInfo findFocusedNode(AccessibilityNodeInfo node) {
//         if (node == null) return null;
//         if (node.isFocused()) {
//             return node;
//         }
//         for (int i = 0; i < node.getChildCount(); i++) {
//             AccessibilityNodeInfo result = findFocusedNode(node.getChild(i));
//             if (result != null) {
//                 return result;
//             }
//         }
//         return null;
//     }

//     private void setTextInFocusedNode(AccessibilityNodeInfo node, String text) {
//         if (node != null && "android.widget.EditText".contentEquals(node.getClassName())) {
//             Bundle arguments = new Bundle();
//             arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
//             node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
//             Log.d(TAG, "Text set in focused input field: " + text);
//         }
//     }
// }

// llll

// package com.example.sandbox;

// import android.accessibilityservice.AccessibilityService;
// import android.accessibilityservice.AccessibilityServiceInfo;
// import android.content.Intent;
// import android.os.Bundle;
// import android.util.Log;
// import android.view.accessibility.AccessibilityEvent;
// import android.view.accessibility.AccessibilityNodeInfo;

// public class MyAccessibilityService extends AccessibilityService {
//     private static final String TAG = "MyAccessibilityService";
//     private String keySent;
//     private String targetPackage = "com.example.sandbox_receiver";

//     @Override
//     public void onServiceConnected() {
//         super.onServiceConnected();
//         AccessibilityServiceInfo info = new AccessibilityServiceInfo();
//         info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_VIEW_FOCUSED | AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;
//         info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
//         setServiceInfo(info);
//         Log.d(TAG, "Accessibility Service Connected");
//     }

//     // @Override
//     // public int onStartCommand(Intent intent, int flags, int startId) {
//     //             Log.d(TAG, "intent : " + intent.toString());

//     //     if (intent != null) {
//     //         keySent = intent.getStringExtra("keySent");
//     //         Log.d(TAG, "Received keySent: " + keySent);


//     //         AccessibilityNodeInfo rootNode = getRootInActiveWindow();
//     //         if (rootNode != null) {
//     //             AccessibilityNodeInfo focusedNode = findFocusedNode(rootNode);
//     //             if (focusedNode != null) {
//     //                 Log.d(TAG, "Focused node one: " + focusedNode.toString());
//     //                 if (keySent != null) {
//     //                     setTextInFocusedNode(focusedNode, keySent);
//     //                 }
//     //             } else {
//     //                 Log.d(TAG, "No focused node found");
//     //             }
//     //         } else {
//     //             Log.d(TAG, "Root node is null");
//     //         }
     
//     //     }
//     //     return START_STICKY;
//     // }
//      @Override
//     public int onStartCommand(Intent intent, int flags, int startId) {
//         if (intent != null) {
//             keySent = intent.getStringExtra("keySent");
//             Log.d(TAG, "Received keySent: " + keySent);

//             // Delay to ensure the target app is in the foreground and ready
//             new android.os.Handler().postDelayed(() -> {
//                 AccessibilityNodeInfo rootNode = getRootInActiveWindow();
//                 if (rootNode != null) {
//                     AccessibilityNodeInfo focusedNode = findFocusedNode(rootNode);
//                     if (focusedNode != null) {
//                         Log.d(TAG, "Focused node: " + focusedNode.toString());
//                         if (keySent != null) {
//                             setTextInFocusedNode(focusedNode, keySent);
//                         }
//                     } else {
//                         Log.d(TAG, "No focused node found");
//                     }
//                 } else {
//                     Log.d(TAG, "Root node is null");
//                 }
//             }, 1000); // Adjust delay as needed
//         }
//         return START_STICKY;
//     }

//     @Override
//     public void onAccessibilityEvent(AccessibilityEvent event) {
//         // Log.d(TAG, "Event: " + event.toString());
        

//         // if (event.getPackageName() != null && event.getPackageName().toString().equals(targetPackage)) {
//         //     AccessibilityNodeInfo rootNode = getRootInActiveWindow();
//         //     if (rootNode != null) {
//         //         AccessibilityNodeInfo focusedNode = findFocusedNode(rootNode);
//         //         if (focusedNode != null) {
//         //             Log.d(TAG, "Focused node: " + focusedNode.toString());
//         //             if (keySent != null) {
//         //                 setTextInFocusedNode(focusedNode, keySent);
//         //                 // sendRefreshBroadcast();
//         //             }
//         //         } else {
//         //             Log.d(TAG, "No focused node found");
//         //         }
//         //     } else {
//         //         Log.d(TAG, "Root node is null");
//         //     }
//         // } else {
//         //     Log.d(TAG, "Event from non-target package: " + (event.getPackageName() != null ? event.getPackageName().toString() : "null"));
//         // }
//     }

//     @Override
//     public void onInterrupt() {
//     }

//     private AccessibilityNodeInfo findFocusedNode(AccessibilityNodeInfo node) {
//         if (node == null) return null;
//         if (node.isFocused()) {
//             return node;
//         }
//         for (int i = 0; i < node.getChildCount(); i++) {
//             AccessibilityNodeInfo result = findFocusedNode(node.getChild(i));
//             if (result != null) {
//                 return result;
//             }
//         }
//         return null;
//     }

//     private void setTextInFocusedNode(AccessibilityNodeInfo node, String text) {
//                                 Log.d(TAG, "Text text: " + text);
//                                                                 Log.d(TAG, "node node: " + node);


//         if (node != null && "android.widget.EditText".contentEquals(node.getClassName())) {
//             Bundle arguments = new Bundle();
//             arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
//                     boolean result = node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);

//             Log.d(TAG, "Text set in focused input field: " + text);
//                     Log.d(TAG, "Text set in focused input field: " + text + " Result: " + result);
//                     node.refresh();

//         }else{
//                                 Log.d(TAG, "No node Found");

//         }
//     }

//     // private void sendRefreshBroadcast() {
//     //     Intent intent = new Intent("com.example.sandbox.REFRESH_UI");
//     //     sendBroadcast(intent);
//     //     Log.d(TAG, "Broadcast sent to refresh UI");
//     // }
// }