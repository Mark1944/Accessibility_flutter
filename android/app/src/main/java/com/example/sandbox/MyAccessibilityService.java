package com.example.sandbox;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";
    private String keySent;
    private String targetPackage = "com.example.sandbox_receiver";

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_VIEW_FOCUSED | AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;
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
        // Not used in this implementation
    }

    @Override
    public void onInterrupt() {
    }

    private void processTextInput(String text) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            AccessibilityNodeInfo focusedNode = findFocusedNode(rootNode);
            if (focusedNode != null) {
                Log.d(TAG, "Focused node: " + focusedNode.toString());
                if ("android.widget.EditText".contentEquals(focusedNode.getClassName())) {
                    setTextInFocusedNode(focusedNode, text);
                } else {
                    Log.d(TAG, "Focused node is not EditText, attempting to refocus");
                    // Attempt to find and focus the correct input field
                    AccessibilityNodeInfo editTextNode = findEditTextNode(rootNode);
                    if (editTextNode != null) {
                        editTextNode.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                        setTextInFocusedNode(editTextNode, text);
                    } else {
                        Log.d(TAG, "No EditText node found");
                    }
                }
            } else {
                Log.d(TAG, "No focused node found");
            }
        } else {
            Log.d(TAG, "Root node is null");
        }
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

    private AccessibilityNodeInfo findEditTextNode(AccessibilityNodeInfo node) {
        if (node == null) return null;
        if ("android.widget.EditText".contentEquals(node.getClassName())) {
            return node;
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo result = findEditTextNode(node.getChild(i));
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private void setTextInFocusedNode(AccessibilityNodeInfo node, String text) {
        if (node != null && "android.widget.EditText".contentEquals(node.getClassName())) {
            // for (int i = 0; i < text.length(); i++) {
            //     final int index = i;
            //     new Handler().postDelayed(() -> {
                    Bundle arguments = new Bundle();
                    arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text.substring(0, index + 1));
                    boolean result = node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                    Log.d(TAG, "Text set in focused input field: " + text.substring(0, index + 1) + " Result: " + result);
                    node.refresh();
                // }, i * 500); // Adjust delay as needed
            // }
        } else {
            Log.d(TAG, "No node Found or Not an EditText");
        }
    }
}
