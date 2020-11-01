import 'dart:async';
import 'dart:html' as html show window;

import 'package:flutter/services.dart';
import 'package:flutter_web_plugins/flutter_web_plugins.dart';

class ZoomSdkWeb {
  static void registerWith(Registrar registrar) {
    final MethodChannel channel = MethodChannel(
      'zoom_sdk',
      const StandardMethodCodec(),
      registrar.messenger,
    );

    final pluginInstance = ZoomSdkWeb();
    channel.setMethodCallHandler(pluginInstance.handleMethodCall);
  }

  Future<dynamic> handleMethodCall(MethodCall call) async {
    switch (call.method) {
      case 'getPlatformVersion':
        return getPlatformVersion();
        break;
      default:
        throw PlatformException(
          code: 'Unimplemented',
          details: 'zoom_sdk for web doesn\'t implement \'${call.method}\'',
        );
    }
  }

  Future<String> getPlatformVersion() {
    final version = html.window.navigator.userAgent;
    return Future.value(version);
  }
}
