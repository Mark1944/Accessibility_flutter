import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  MyAppState createState() => MyAppState();
}

class MyAppState extends State<MyApp> {
  static const platform =
      MethodChannel('com.example.keyevents/receiveKeyEvent');
  String _receivedData = "No data received";

  @override
  void initState() {
    super.initState();
    platform.setMethodCallHandler(_handleMethodCall);
  }

  Future<void> _handleMethodCall(MethodCall call) async {
    switch (call.method) {
      case 'receiveKeyEvent':
        setState(() {
          _receivedData = call.arguments;
        });
        break;
      default:
        debugPrint('Method not implemented: ${call.method}');
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Key Event Receiver'),
        ),
        body: Center(
          child: Text(_receivedData),
        ),
      ),
    );
  }
}
