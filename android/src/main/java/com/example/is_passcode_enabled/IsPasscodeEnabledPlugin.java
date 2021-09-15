package com.example.is_passcode_enabled;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** IsPasscodeEnabledPlugin */
public class IsPasscodeEnabledPlugin implements FlutterPlugin, MethodCallHandler {
     private static Context context=null;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    
    final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter_lockscreen_check");
    context=flutterPluginBinding.getApplicationContext();
    channel.setMethodCallHandler(new IsPasscodeEnabledPlugin());
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {

    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_lockscreen_check");
    channel.setMethodCallHandler(new IsPasscodeEnabledPlugin());

   }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result)
    {

        if (call.method.equals("checkLockScreenAvailable")) {
       //     Log.d("Application",String.valueOf(isDeviceScreenLocked(context)));
            result.success(isDeviceScreenLocked(context));
        } else {
            result.error("","Not Method Found","Could Not Get Platform");
        }
    }
  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
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
