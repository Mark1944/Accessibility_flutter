import 'package:flutter/material.dart';

import '../utils/method_channel/accessibility_service.dart';

class HomeScreen extends StatelessWidget {
   const HomeScreen({super.key});


  @override
  Widget build(BuildContext context) {
      TextEditingController keyController = TextEditingController();

    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            SizedBox(
              width:300,
              child: TextFormField(
                keyboardType: TextInputType.number,
                controller: keyController,
                decoration: InputDecoration(
                  hintText: "Key",
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10),
                  ),
                ),
              ),
            ),
            const SizedBox(height: 10,),
            ElevatedButton(
                onPressed: () {
                  AccessibilityService.sendText(keyController.text);
                },
                child: const Text("Send Keys")),
          ],
        ),
      ),
    );
  }
}
