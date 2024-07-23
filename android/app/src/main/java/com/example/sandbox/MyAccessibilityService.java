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
            new Handler().postDelayed(() -> {
                processTextInput(keySent);
            }, 1000); // Adjust delay as needed
        }
        return START_STICKY;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Process only focus changes
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_FOCUSED) {
            AccessibilityNodeInfo rootNode = getRootInActiveWindow();
            if (rootNode != null) {
                AccessibilityNodeInfo focusedNode = findFocusedNode(rootNode);
                if (focusedNode != null) {
                    Log.d(TAG, "New focused node: " + focusedNode.toString());
                    
                    if (!focusedNode.equals(lastFocusedNode)) {
                        lastFocusedNode = focusedNode;

                        if (keySent != null) {
                            updateTextInNode(focusedNode, keySent);
                        }
                    }
                } else {
                    Log.d(TAG, "No focused node found");
                }
            } else {
                Log.d(TAG, "Root node is null");
            }
        }
    }

    @Override
    public void onInterrupt() {
    }

    private void processTextInput(String text) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                        Log.d(TAG, "RootNode node: " + rootNode.toString());

        if (rootNode != null) {
            AccessibilityNodeInfo focusedNode = findFocusedNode(rootNode);
            if (focusedNode != null) {
                Log.d(TAG, "Focused node: " + focusedNode.toString());
                if ("android.widget.EditText".contentEquals(focusedNode.getClassName())) {
                    setTextInFocusedNode(focusedNode, text);
                } else {
                    Log.d(TAG, "Focused node is not EditText, attempting to refocus");
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

        //Todo: Look ath this here, how to put one work or one text and also add spacing
        Log.d(TAG, "node node node: " + node);
                Log.d(TAG, "text.length(): " + text.length());


        if(text.length() > 1){

setFullTextInFocusedNode(node, text);
        }else{
        if (node != null && "android.widget.EditText".contentEquals(node.getClassName())) {
            // Use StringBuilder to accumulate the text to be set
            final CharSequence currentText = node.getText();
            Log.d(TAG, "CurrentText set in focused input field: " + currentText);
            final AccessibilityNodeInfo finalNode = node;
            final StringBuilder accumulatedText = new StringBuilder(currentText != null ? currentText.toString() : "");

            // Loop to add one character at a time with delay
            for (int i = 0; i < text.length(); i++) {
                final int index = i;
                new Handler().postDelayed(() -> {
                    // Append the next character to the accumulated text
                    accumulatedText.append(text.charAt(index));
                    String newText = accumulatedText.toString();
                    
                    // Set the text in the node
                    Bundle arguments = new Bundle();
                    arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, newText);
                    boolean result = finalNode.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                    Log.d(TAG, "Text set in focused input field: " + newText + " Result: " + result);

                    // Wait for a short delay before checking the updated text
                    new Handler().postDelayed(() -> {
                        AccessibilityNodeInfo updatedNode = getRootInActiveWindow();
                        if (updatedNode != null) {
                            AccessibilityNodeInfo focusedNode = findFocusedNode(updatedNode);
                            if (focusedNode != null) {
                                CharSequence updatedText = focusedNode.getText();
                                Log.d(TAG, "Updated text in focused input field: " + updatedText);
                            }
                        }
                    }, 500); // Adjust delay as needed

                    finalNode.refresh();
                }, i * 500); // Adjust delay as needed
            }
        } else {
            Log.d(TAG, "No node Found or Not an EditText");
        }
        }
    }

    private void setFullTextInFocusedNode(AccessibilityNodeInfo node, String text) {
        if (node != null && "android.widget.EditText".contentEquals(node.getClassName())) {
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
            Log.d(TAG, "Text set in focused input field: " + text);
        }
    }

    private void updateTextInNode(AccessibilityNodeInfo node, String text) {
        if (node != null && "android.widget.EditText".contentEquals(node.getClassName())) {
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            boolean result = node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
            Log.d(TAG, "Text set in focused input field: " + text + " Result: " + result);
        } else {
            Log.d(TAG, "No valid EditText node found");
        }
    }
}
