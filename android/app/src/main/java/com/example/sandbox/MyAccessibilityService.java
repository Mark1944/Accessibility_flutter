package com.example.sandbox;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.content.Intent;
import android.util.Log;


public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // No changes here; implement as needed

            Log.d("AccessibilityService", "Event: " + event.toString());

    }

    @Override
    public void onInterrupt() {
        // No changes here; implement as needed
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("text")) {
            String text = intent.getStringExtra("text");
            // Use this text as needed in your service
            sendTextToAppB(text);
        }
        return START_STICKY;
    }

    private void sendTextToAppB(String text) {
        // Implement your logic to find the appropriate node and send the text
        // This example is for demonstration; you need to implement the actual logic
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            findAndSetText(rootNode, text);
        }
    }

    private void findAndSetText(AccessibilityNodeInfo node, String text) {
        if (node == null) return;
        if (node.isEditable()) {
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            findAndSetText(node.getChild(i), text);
        }

            Log.d("AccessibilityService", "Data sent to Flutter: " + text);

    }
}
