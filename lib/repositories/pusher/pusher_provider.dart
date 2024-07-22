import 'dart:convert';
import 'dart:developer';

import 'package:flutter/services.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:pusher_channels_flutter/pusher_channels_flutter.dart';

class PusherConnectionStateNotifier extends StateNotifier<String> {
  final Ref ref;
  PusherConnectionStateNotifier(this.ref) : super('Disconnected');
  PusherChannelsFlutter pusher = PusherChannelsFlutter.getInstance();

  Future<void> connect({required String channelName}) async {
    ref.read(pusherConnectionLoader.notifier).state = true;

    await pusher.init(
      apiKey: dotenv.env['PUSHER_APP_KEY']!,
      cluster: dotenv.env['PUSHER_APP_CLUSTER']!,
      onConnectionStateChange: onConnectionStateChange,
      onError: onErrors,
      onSubscriptionSucceeded: onSubscriptionSucceeded,
      onEvent: onEvent,
      onSubscriptionError: onSubscriptionError,
      // onAuthorizer: onAuthorizer,
    );
    await pusher.subscribe(channelName: channelName);
    await pusher.connect();
  }

  void onConnectionStateChange(dynamic currentState, dynamic previousState) {
    log("Connection: $currentState");
  }

  void onErrors(String message, int? code, dynamic e) {
    ref.read(pusherConnectionLoader.notifier).state = false;
  }

  Future<void> onEvent(PusherEvent event) async {
    log("onEvent: $event");

    String message = '${event.data}';

    Map<String, dynamic> messageMap = jsonDecode(message);

    // log("messageMap: $messageMap");

    if (event.eventName == "enterData") {
      sendAccessibilityData(
        messageMap['keySent'],
      );
    }

    if (event.eventName == "nextButtonClick") {
      nextButtonPress(
                messageMap['buttonPress'],

      );
    }

// To disconnect once the token is gotten5
    // await pusher.disconnect();
  }

  Future<void> sendAccessibilityData(String keySent) async {
    const platform = MethodChannel('com.example.sandbox/accessibility');
    // static const platform = MethodChannel('com.example.sandbox/accessibility');

    try {
      await platform.invokeMethod('sendText', {
        'keySent': keySent,
      });
    } on PlatformException catch (e) {
      log("Failed to send data: '${e.message}'.");
    }
  }

  Future<void> nextButtonPress(bool buttonPress) async {
    const platform = MethodChannel('com.example.sandbox/accessibility');
 

    try {
      await platform.invokeMethod('tabPress', {
        'buttonPress': buttonPress,
      });
    } on PlatformException catch (e) {
      log("Failed to send data: '${e.message}'.");
    }
  }

  void onSubscriptionSucceeded(String channelName, dynamic data) {
    log("onSubscriptionSucceeded: $channelName data: $data");
  }

  void onSubscriptionError(String message, dynamic e) {
    log("onSubscriptionError: $message Exception: $e");
    ref.read(pusherConnectionLoader.notifier).state = false;
  }
}

final pusherConnectionStateProvider =
    StateNotifierProvider<PusherConnectionStateNotifier, String>((ref) {
  return PusherConnectionStateNotifier(ref);
});

final pusherConnectionLoader = StateProvider((ref) => false);
