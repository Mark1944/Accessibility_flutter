import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_hooks/flutter_hooks.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: HomeScreen(),
    );
  }
}

class HomeScreen extends HookWidget {
  final TextEditingController _usernameController = TextEditingController(text: "rerererer");
  final TextEditingController _passwordController = TextEditingController();

  HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    useOnAppLifecycleStateChange((pref, state) {
      log("state  $state");
      if (state == AppLifecycleState.hidden) {
        Future.delayed(const Duration(seconds: 5), () {
          log('Hello after 2 seconds');

          if (_usernameController.text != "") {
            sendAccessibilityData(
              _usernameController.text,
              _passwordController.text,
            );
          }
        });
      }
    });
    return Scaffold(
      appBar: AppBar(
        title: const Text('App A'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              
              key: const Key('username_field'),
              controller: _usernameController,
              decoration: const InputDecoration(labelText: 'Username'),
            ),
            TextField(
              controller: _passwordController,
              decoration: const InputDecoration(labelText: 'Password'),
              obscureText: true,
            ),
            ElevatedButton(
              onPressed: () {
                // Future.delayed(const Duration(seconds: 5), () {
                //   log('Hello after 2 seconds');

                //   sendAccessibilityData(
                //     _usernameController.text,
                //     _passwordController.text,
                //   );
                // });
              },
              child: const Text('Send Data to App B'),
            ),
          ],
        ),
      ),
    );
  }

  Future<void> sendAccessibilityData(String username, String password) async {
    const platform = MethodChannel('com.example.sandbox/accessibility');
    // static const platform = MethodChannel('com.example.sandbox/accessibility');

    try {
      await platform.invokeMethod('sendText', {
        'username': username,
        'password': password,
      });
    } on PlatformException catch (e) {
      log("Failed to send data: '${e.message}'.");
    }
  }
}
