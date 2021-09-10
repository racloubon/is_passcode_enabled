import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:is_passcode_enabled/is_passcode_enabled.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool _isPasscodeEnabled;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    bool isPasscodeEnabled;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      isPasscodeEnabled = await IsPasscodeEnabled.isPasscodeEnabled;
    } on PlatformException {
      // TO DO: handle error
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _isPasscodeEnabled = isPasscodeEnabled;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('is_passcode_enabled example app'),
        ),
        body: Center(
          child: Text('Passcode is enabled: $_isPasscodeEnabled\n'),
        ),
      ),
    );
  }
}
