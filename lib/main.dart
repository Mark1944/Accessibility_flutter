import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = MethodChannel('com.example.keyevents/receiveKeyEvent');

  String _response = 'No response yet';

  Future<void> _receiveKeyEvent() async {
    try {
      final String result = await platform.invokeMethod('receiveKeyEvent');
      setState(() {
        _response = result;
        print(result);
      });
    } on PlatformException catch (e) {
      setState(() {
        _response = "Failed to receive key event: '${e.message}'.";
      });
    }
  }

  Future<void> _sendKeyEvent(int keyCode) async {
    try {
      final String result = await platform.invokeMethod('sendKeyEvent', {'keyCode': keyCode});
      setState(() {
        _response = result;
      });
    } on PlatformException catch (e) {
      setState(() {
        _response = "Failed to send key event: '${e.message}'.";
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('Response: $_response'),
            ElevatedButton(
              onPressed: _receiveKeyEvent,
              child: Text('Receive Key Event'),
            ),
            ElevatedButton(
              onPressed: () => _sendKeyEvent(42), // Replace with desired keyCode
              child: Text('Send Key Event'),
            ),
          ],
        ),
      ),
    );
  }
}
