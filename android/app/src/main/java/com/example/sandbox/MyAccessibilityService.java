package com.example.sandbox;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.KeyEvent;
import android.os.SystemClock;
import android.app.Instrumentation;




public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";
    private String keySent;
    private String targetPackage = "com.anywairtv";
    private AccessibilityNodeInfo lastFocusedNode;

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED 
                | AccessibilityEvent.TYPE_VIEW_FOCUSED 
                | AccessibilityEvent.TYPE_VIEW_CLICKED 
                | AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.packageNames = new String[]{targetPackage}; // Ensuring we only listen to events from the target package
        setServiceInfo(info);
        Log.d(TAG, "Accessibility Service Connected");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            keySent = intent.getStringExtra("keySent");
            Log.d(TAG, "Received keySent: " + keySent);

            // Delay to ensure the target app is in the foreground and ready
            // new Handler().postDelayed(() -> {
                processTextInput(keySent);
            // }, 1000); // Adjust delay as needed
        }
        return START_STICKY;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Process only focus changes
        // if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_FOCUSED) {
        //     AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        //     if (rootNode != null) {
        //         AccessibilityNodeInfo focusedNode = findFocusedNode(rootNode);
        //         if (focusedNode != null) {
        //             Log.d(TAG, "New focused node: " + focusedNode.toString());
                    
        //             if (!focusedNode.equals(lastFocusedNode)) {
        //                 lastFocusedNode = focusedNode;

        //                 if (keySent != null) {
        //                     updateTextInNode(focusedNode, keySent);
        //                 }
        //             }
        //         } else {
        //             Log.d(TAG, "No focused node found");
        //         }
        //     } else {
        //         Log.d(TAG, "Root node is null");
        //     }
        // }
    }

    @Override
    public void onInterrupt() {
    }

    // private void processTextInput(String text) {
    //     AccessibilityNodeInfo rootNode = getRootInActiveWindow();
    //                     Log.d(TAG, "RootNode node: " + rootNode.toString());

    //     if (rootNode != null) {
    //         AccessibilityNodeInfo focusedNode = findFocusedNode(rootNode);
    //         if (focusedNode != null) {
    //             Log.d(TAG, "Focused node: " + focusedNode.toString());
    //             if ("android.widget.EditText".contentEquals(focusedNode.getClassName())) {
    //                 setTextInFocusedNode(focusedNode, text);
    //             } else {
    //                 Log.d(TAG, "Focused node is not EditText, attempting to refocus");
    //                 AccessibilityNodeInfo editTextNode = findEditTextNode(rootNode);
    //                 if (editTextNode != null) {
    //                     editTextNode.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
    //                     setTextInFocusedNode(editTextNode, text);
    //                 } else {
    //                     Log.d(TAG, "No EditText node found");
    //                 }
    //             }
    //         } else {
    //             Log.d(TAG, "No focused node found");
    //         }
    //     } else {
    //         Log.d(TAG, "Root node is null");
    //     }
    // }

    private void processTextInput(String text) {
        if (text == null || text.isEmpty()) {
            Log.d(TAG, "Text input is null or empty");
            return;
        }

        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode == null) {
            Log.d(TAG, "Root node is null");
            return;
        }

        // Find the first editable text field and set its text
        setNodeText(rootNode, text);
    }

    private void setNodeText(AccessibilityNodeInfo rootNode, String text) {
                                 Log.d(TAG, "Root node is null " + text);

        if (rootNode == null) return;

        for (int i = 0; i < rootNode.getChildCount(); i++) {
            AccessibilityNodeInfo childNode = rootNode.getChild(i);
            if (childNode != null) {
                if (childNode.isEditable()) {
                     CharSequence existingText = childNode.getText();
                                 Log.d(TAG, "Root node is null " + existingText);

                    Bundle arguments = new Bundle();
                    arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
                    childNode.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                    return; // Assume we only need to set text in the first editable field found
                }
                setNodeText(childNode, text);
            }
        }
    }


 

  
}
