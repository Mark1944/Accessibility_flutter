// package com.example.sandbox;

// import android.accessibilityservice.AccessibilityService;
// import android.accessibilityservice.AccessibilityServiceInfo;
// import android.view.accessibility.AccessibilityEvent;
// import android.view.KeyEvent;

// public class KeyEventService extends AccessibilityService {
//     @Override
//     public void onAccessibilityEvent(AccessibilityEvent event) {}

//     @Override
//     public void onInterrupt() {}

//     @Override
//     protected void onServiceConnected() {
//         AccessibilityServiceInfo info = new AccessibilityServiceInfo();
//         info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
//         info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
//         info.notificationTimeout = 100;
//         setServiceInfo(info);
//     }

//     public void sendKeyEvent(int keyCode) {
//         KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
//         // Dispatch the key event to the focused app
//     }
// }
