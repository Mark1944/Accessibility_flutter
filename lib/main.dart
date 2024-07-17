// import 'package:flutter/material.dart';
// import 'package:sandbox/screen/home_screen.dart';

// void main(){
//   runApp(const MyApp());
// }

// class MyApp extends StatelessWidget {
//   const MyApp({super.key});

//   @override
//   Widget build(BuildContext context) {
//     return  const MaterialApp(
//       home: HomeScreen(),
//     );
//   }
// }
import 'dart:developer';




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
  MyHomePageState createState() => MyHomePageState();
}

class MyHomePageState extends State<MyHomePage> {
  static const platform = MethodChannel('com.example.sandbox/accessibility');

  Future<void> sendText() async {
    try {
      await platform.invokeMethod('sendTextToAppB', {'text': 'Hello from App A'});
    } on PlatformException catch (e) {
      log("Failed to send text: '${e.message}'.");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('App A'),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: sendText,
          child: const Text('Send Text to App B'),
        ),
      ),
    );
  }
}
