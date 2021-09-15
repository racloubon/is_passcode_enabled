import 'dart:async';

import 'package:flutter/services.dart';

class IsPasscodeEnabled {
  static const MethodChannel _channel = const MethodChannel('is_passcode_enabled');

  static Future<bool> get isPasscodeEnabled async {
    final bool isPasscodeEnabled = await _channel.invokeMethod('is_passcode_enabled');
    return isPasscodeEnabled;
  }
}
