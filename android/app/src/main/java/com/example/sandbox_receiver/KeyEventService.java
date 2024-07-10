// package com.example.sandbox_receiver;

// import android.accessibilityservice.AccessibilityService;
// import android.accessibilityservice.AccessibilityServiceInfo;
// import android.view.accessibility.AccessibilityEvent;

//     public class KeyEventService extends AccessibilityService {
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

// }
