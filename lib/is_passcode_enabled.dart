import 'dart:async';

import 'package:flutter/services.dart';

class IsPasscodeEnabled {
  static const MethodChannel _channel =
      const MethodChannel('is_passcode_enabled');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<bool> get isPasscodeEnabled async {
    final bool isPasscodeEnabled =
        await _channel.invokeMethod('isPasscodeEnabled');
    return isPasscodeEnabled;
  }
}
