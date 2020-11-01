import 'dart:async';

import 'package:flutter/services.dart';

class ZoomSdk {
  static const MethodChannel _channel = const MethodChannel('zoom_sdk');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
