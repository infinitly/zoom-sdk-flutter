# Setup
## Android
Change the minimum Android SDK version to at the minimum 21 in your android/app/build.gradle file.

```
minSdkVersion 21
```

## iOS
Add two rows to the ios/Runner/Info.plist:

- one with the key Privacy - Camera Usage Description and a usage description.
- and one with the key Privacy - Microphone Usage Description and a usage description.

Or in text format add the key:

```
<key>NSCameraUsageDescription</key>
<string>Need to use the camera for call</string>
<key>NSMicrophoneUsageDescription</key>
<string>Need to use the microphone for call</string>
```

# Zoom SDK
## Version
- Android: `v5.2.41727.0928`
- iOS: `v5.2.41739.1022`

## Upgrade Android
SDK Android: [zoom/zoom-sdk-android](https://marketplace.zoom.us/docs/sdk/native-sdks/android/getting-started/install-sdk)

```
curl https://github.com/zoom/zoom-sdk-android/archive/master.zip
unzip zoom-sdk-android-master.zip

cp zoom-sdk-android-master/mobilertc-android-studio/commonlib/commonlib.aar android/libs/commonlib/
cp zoom-sdk-android-master/mobilertc-android-studio/mobilertc/mobilertc.aar android/libs/mobilertc/
```

## Upgrade iOS
SDK iOS: [zoom/zoom-sdk-ios](https://marketplace.zoom.us/docs/sdk/native-sdks/iOS/getting-started/install-sdk)

```
curl https://github.com/zoom/zoom-sdk-ios/archive/master.zip
unzip zoom-sdk-ios-master.zip

cp zoom-sdk-ios-master/lib/MobileRTC.framework ios/
cp zoom-sdk-ios-master/lib/MobileRTCResources.bundle ios/
cp zoom-sdk-ios-master/lib/MobileRTCScreenShare.framework ios/
```
