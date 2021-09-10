import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:is_passcode_enabled/is_passcode_enabled.dart';

void main() {
  const MethodChannel channel = MethodChannel('is_passcode_enabled');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await IsPasscodeEnabled.platformVersion, '42');
  });
}
