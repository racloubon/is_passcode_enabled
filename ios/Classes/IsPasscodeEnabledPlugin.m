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
  if ([@"isPasscodeEnabled" isEqualToString:call.method]) {
   LAContext *context = [LAContext new];
   NSError *error;
   BOOL passcodeEnabled = [context canEvaluatePolicy:LAPolicyDeviceOwnerAuthentication error:&error];
   if (error != nil) {
       // TO DO: handle error
   } else if (passcodeEnabled) {
    result([NSNumber numberWithBool:YES]);
   } else {
    result([NSNumber numberWithBool:NO]);
   }
  } else {
    result(FlutterMethodNotImplemented);
  }
}


@end
