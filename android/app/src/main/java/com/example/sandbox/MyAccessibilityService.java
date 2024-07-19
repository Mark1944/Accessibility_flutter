package com.example.sandbox;


import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";
    private String username;
    private String password;

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_VIEW_FOCUSED | AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
        Log.d(TAG, "Accessibility Service Connected");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            username = intent.getStringExtra("username");
            password = intent.getStringExtra("password");
            Log.d(TAG, "Received username: " + username + ", password: " + password);
        }
        return START_STICKY;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "Event: " + event.toString());
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            AccessibilityNodeInfo focusedNode = findFocusedNode(rootNode);
            if (focusedNode != null) {
                Log.d(TAG, "Focused node: " + focusedNode.toString());
                if (username != null && password != null) {
                    setTextInFocusedNode(focusedNode, username);
                    // setTextInFocusedNode(focusedNode, password);
                }
            } else {
                Log.d(TAG, "No focused node found");
            }
        } else {
            Log.d(TAG, "Root node is null");
        }
    }

    @Override
    public void onInterrupt() {
    }

    private AccessibilityNodeInfo findFocusedNode(AccessibilityNodeInfo node) {
        if (node == null) return null;
        if (node.isFocused()) {
            return node;
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo result = findFocusedNode(node.getChild(i));
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private void setTextInFocusedNode(AccessibilityNodeInfo node, String text) {
        if (node != null && "android.widget.EditText".contentEquals(node.getClassName())) {
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
            Log.d(TAG, "Text set in focused input field: " + text);
        }
    }
}

// import android.accessibilityservice.AccessibilityService;
// import android.accessibilityservice.AccessibilityServiceInfo;
// import android.content.Intent;
// import android.os.Bundle;
// import android.util.Log;
// import android.view.accessibility.AccessibilityEvent;
// import android.view.accessibility.AccessibilityNodeInfo;

// import java.util.List;

// public class MyAccessibilityService extends AccessibilityService {
//     private static final String TAG = "MyAccessibilityService";
//     private String username;
//     private String password;
//     private boolean buttonPressed = false;

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
//             buttonPressed = intent.getBooleanExtra("buttonPressed", false);
//             Log.d(TAG, "Received username: " + username + ", password: " + password + ", buttonPressed: " + buttonPressed);
//         }
//         return START_STICKY;
//     }

//     @Override
//     public void onAccessibilityEvent(AccessibilityEvent event) {
//         Log.d(TAG, "Event: " + event.toString());
//         AccessibilityNodeInfo rootNode = getRootInActiveWindow();
//         if (rootNode != null) {
//             if (buttonPressed) {
//                 buttonPressed = false; // Reset the flag
//                 handleButtonPress(rootNode);
//             }
//         } else {
//             Log.d(TAG, "Root node is null");
//         }
//     }

//     @Override
//     public void onInterrupt() {
//     }

//     private void handleButtonPress(AccessibilityNodeInfo rootNode) {
//         AccessibilityNodeInfo focusedNode = findFocusedNode(rootNode);
//         if (focusedNode != null) {
//             Log.d(TAG, "Focused node: " + focusedNode.toString());
//             AccessibilityNodeInfo nextNode = findNextEditableNode(rootNode, focusedNode);
//             if (nextNode != null) {
//                 focusNode(nextNode);
//                 Log.d(TAG, "Focused next node: " + nextNode.toString());
//             } else {
//                 Log.d(TAG, "No next editable node found");
//             }
//         } else {
//             Log.d(TAG, "No focused node found");
//         }
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

//     private AccessibilityNodeInfo findNextEditableNode(AccessibilityNodeInfo rootNode, AccessibilityNodeInfo currentNode) {
//         boolean foundCurrent = false;
//         return findNextEditableNodeRecursive(rootNode, currentNode, foundCurrent);
//     }

//     private AccessibilityNodeInfo findNextEditableNodeRecursive(AccessibilityNodeInfo node, AccessibilityNodeInfo currentNode, boolean foundCurrent) {
//         if (node == null) return null;
//         if (node.equals(currentNode)) {
//             foundCurrent = true;
//         } else if (foundCurrent && "android.widget.EditText".contentEquals(node.getClassName())) {
//             return node;
//         }
//         for (int i = 0; i < node.getChildCount(); i++) {
//             AccessibilityNodeInfo result = findNextEditableNodeRecursive(node.getChild(i), currentNode, foundCurrent);
//             if (result != null) {
//                 return result;
//             }
//         }
//         return null;
//     }

//     private void focusNode(AccessibilityNodeInfo node) {
//         if (node != null && "android.widget.EditText".contentEquals(node.getClassName())) {
//             node.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
//             Log.d(TAG, "Node focused: " + node.toString());
//         }
//     }
// }
