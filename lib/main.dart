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
  String receivedKey = "No key received";

  static const platform =
      MethodChannel('com.example.keyevents/receiveKeyEvent');

  @override
  void initState() {
    super.initState();
    _receiveKeyEvent();
  }

  void _receiveKeyEvent() {
    platform.setMethodCallHandler((MethodCall call) async {
      if (call.method == "receiveKeyEvent") {
        receivedKey = await call.arguments['keyCode'];
        print("$receivedKey===========");
        setState(()  {

        });
      }
    });
  }
  static const platforms = MethodChannel('com.example.keyevents/sendKeyEvent');

  static Future<void> sendText(String text) async {
    try {
      await platforms.invokeMethod('sendKeyEvent', {'keyCode': text});
    } on PlatformException catch (e) {
      print("Failed to send text: '${e.message}'.");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text("Home page = $receivedKey"),
            const SizedBox(
              height: 10,
            ),
            ElevatedButton(
                onPressed: () {
                  _receiveKeyEvent();
                  // sendText("123456");
                },
                child: const Text("Check"))
          ],
        ),
      ),
    );
  }
}
