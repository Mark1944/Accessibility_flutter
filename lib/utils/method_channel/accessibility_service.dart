import 'dart:developer';

import 'package:flutter/services.dart';

class AccessibilityService {

  static const platform = MethodChannel('com.example.keyevents/sendKeyEvent');

  static Future<void> sendText(String text) async {
    try {
      await platform.invokeMethod('sendKeyEvent', {'keyCode': text});
    } on PlatformException catch (e) {
      log("Failed to send text: '${e.message}'.");
    }
  }
}
 