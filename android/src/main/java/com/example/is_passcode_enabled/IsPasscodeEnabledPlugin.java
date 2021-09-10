package com.example.is_passcode_enabled;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** IsPasscodeEnabledPlugin */
public class IsPasscodeEnabledPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private static Context context=null;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "is_passcode_enabled");
    channel.setMethodCallHandler(this);
    context=flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("isPasscodeEnabled")) {
      result.success(isDeviceScreenLocked(context));
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}


private static boolean isDeviceScreenLocked(Context appCon) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      return isDeviceLocked(appCon);
    } else {
      return isPatternSet(appCon) || isPassOrPinSet(appCon);
    }
  }

  /**
   * @return true if pattern set, false if not (or if an issue when checking)
   */
  private static boolean isPatternSet(Context appCon) {

    ContentResolver cr = appCon.getContentResolver();
    try {
      int lockPatternEnable = Settings.Secure.getInt(cr, Settings.Secure.LOCK_PATTERN_ENABLED);
      return lockPatternEnable == 1;
    } catch (Settings.SettingNotFoundException e) {
      return false;
    }
  }

  /**
   * @return true if pass or pin set
   */
  private static boolean isPassOrPinSet(Context appCon) {
    KeyguardManager keyguardManager = (KeyguardManager) appCon.getSystemService(Context.KEYGUARD_SERVICE); //api 16+
    return keyguardManager.isKeyguardSecure();
  }

  /**
   * @return true if pass or pin or pattern locks screen
   */
  @TargetApi(23)
  private static boolean isDeviceLocked(Context appCon) {
    KeyguardManager keyguardManager = (KeyguardManager) appCon.getSystemService(Context.KEYGUARD_SERVICE); //api 23+
    return keyguardManager.isDeviceSecure();
  }

}
