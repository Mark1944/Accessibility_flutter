// package com.example.sandbox;


// import android.accessibilityservice.AccessibilityService;
// import android.accessibilityservice.AccessibilityServiceInfo;
// import android.content.Intent;
// import android.os.Bundle;
// import android.util.Log;
// import android.view.accessibility.AccessibilityEvent;
// import android.view.accessibility.AccessibilityNodeInfo;

// public class ButtonAccessibilityService extends AccessibilityService {
//     private static final String TAG = "MyAccessibilityService";
//     private boolean buttonPress;

//     @Override
//     public void onServiceConnected() {
//         super.onServiceConnected();
//         AccessibilityServiceInfo info = new AccessibilityServiceInfo();
//         info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_VIEW_FOCUSED | AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;
//         info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
//         setServiceInfo(info);
//         Log.d(TAG, "Accessibility Service Connected");
//     }

//    @Override
//     public int onStartCommand(Intent intent, int flags, int startId) {
//         if (intent != null) {
//             boolean buttonPress = intent.getBooleanExtra("buttonPress", false);

//             if (buttonPress) {
//                 focusNextFieldAndUnfocusCurrent();
//             }
//         }
//         return START_STICKY;
//     }

//     private void focusNextFieldAndUnfocusCurrent() {
//         AccessibilityNodeInfo rootNode = getRootInActiveWindow();
//         if (rootNode != null) {
//             AccessibilityNodeInfo currentFocusedNode = getCurrentFocusedNode(rootNode);
//             if (currentFocusedNode != null) {
//                 AccessibilityNodeInfo nextNode = findNextEditableNode(rootNode, currentFocusedNode);
//                 if (nextNode != null) {
//                     // Unfocus the current node
//                     currentFocusedNode.performAction(AccessibilityNodeInfo.ACTION_CLEAR_FOCUS);

//                     // Focus the next node
//                     nextNode.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
//                 }
//             }
//         }
//     }

//     private AccessibilityNodeInfo getCurrentFocusedNode(AccessibilityNodeInfo node) {
//         if (node == null) return null;
//         if (node.isFocused()) return node;

//         for (int i = 0; i < node.getChildCount(); i++) {
//             AccessibilityNodeInfo result = getCurrentFocusedNode(node.getChild(i));
//             if (result != null) {
//                 return result;
//             }
//         }
//         return null;
//     }

//     private AccessibilityNodeInfo findNextEditableNode(AccessibilityNodeInfo rootNode, AccessibilityNodeInfo currentNode) {
//         if (rootNode == null) return null;

//         for (int i = 0; i < rootNode.getChildCount(); i++) {
//             AccessibilityNodeInfo child = rootNode.getChild(i);
//             if (child.isEditable() && !child.equals(currentNode)) {
//                 return child;
//             }
//             AccessibilityNodeInfo result = findNextEditableNode(child, currentNode);
//             if (result != null) {
//                 return result;
//             }
//         }
//         return null;
//     }
// }

package com.example.sandbox;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class ButtonAccessibilityService extends AccessibilityService {
    private static final String TAG = "ButtonAccessibilityService";
    private boolean buttonPress;


    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_VIEW_FOCUSED | AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
        Log.d(TAG, "Accessibility Service Connected button");
    }
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
                Log.d(TAG, "Event: " + event.toString());

        // Handle accessibility events if needed
                        Log.d(TAG, "Focused node eventevent event: " + event.toString());


                        focusNextFieldAndUnfocusCurrent();

    }

    @Override
    public void onInterrupt() {
        // Handle service interruption if needed
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
             buttonPress = intent.getBooleanExtra("buttonPress", false);

          
        }
        return START_STICKY;
    }

    private void focusNextFieldAndUnfocusCurrent() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            AccessibilityNodeInfo currentFocusedNode = getCurrentFocusedNode(rootNode);
            if (currentFocusedNode != null) {
                AccessibilityNodeInfo nextNode = findNextEditableNode(rootNode, currentFocusedNode);
                if (nextNode != null) {
                    // Unfocus the current node
                    currentFocusedNode.performAction(AccessibilityNodeInfo.ACTION_CLEAR_FOCUS);

                    // Focus the next node
                    nextNode.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                }
            }
        }
    }

    private AccessibilityNodeInfo getCurrentFocusedNode(AccessibilityNodeInfo node) {
        if (node == null) return null;
        if (node.isFocused()) return node;

        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo result = getCurrentFocusedNode(node.getChild(i));
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private AccessibilityNodeInfo findNextEditableNode(AccessibilityNodeInfo rootNode, AccessibilityNodeInfo currentNode) {
        if (rootNode == null) return null;

        for (int i = 0; i < rootNode.getChildCount(); i++) {
            AccessibilityNodeInfo child = rootNode.getChild(i);
            if (child.isEditable() && !child.equals(currentNode)) {
                return child;
            }
            AccessibilityNodeInfo result = findNextEditableNode(child, currentNode);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
