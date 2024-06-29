import 'package:flutter/services.dart';

class AccessibilityService {

  static const platform = MethodChannel('com.example.keyevents');

  static Future<void> sendText(String text) async {
    try {
      await platform.invokeMethod('sendText', {'text': text});
    } on PlatformException catch (e) {
      print("Failed to send text: '${e.message}'.");
    }
  }
}
