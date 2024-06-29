import 'package:flutter/material.dart';

import '../utils/method_channel/accessibility_service.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return  Scaffold(
      body: Center(
        child: ElevatedButton(onPressed: () {
        AccessibilityService.sendText("123123");

          }, child: const Text("Send Keys")),
      ),
    );
  }
}
