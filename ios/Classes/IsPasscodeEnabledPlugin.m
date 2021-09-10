#import "IsPasscodeEnabledPlugin.h"
#import <LocalAuthentication/LocalAuthentication.h>

@implementation IsPasscodeEnabledPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"is_passcode_enabled"
            binaryMessenger:[registrar messenger]];
  IsPasscodeEnabledPlugin* instance = [[IsPasscodeEnabledPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
  } else if ([@"isPasscodeEnabled" isEqualToString:call.method]) {
   LAContext *context = [LAContext new];
   NSError *error;
   BOOL passcodeEnabled = [context canEvaluatePolicy:LAPolicyDeviceOwnerAuthentication error:&error];

   if (error != nil) {
       // do something with the error
   } else if (passcodeEnabled) {
    NSLog(@"coucou its false");
    result([true]);
   } else {
      NSLog(@"coucou its false");
      result([false]);
   }
  } else {
    result(FlutterMethodNotImplemented);
  }
}





@end
