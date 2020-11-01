package net.metainnovative.zoom_sdk

import android.content.Context
import android.view.View
import android.widget.TextView
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView
import us.zoom.sdk.ZoomSDKAuthenticationListener

class ZoomSdkPlugin(private val context: Context, messenger: BinaryMessenger) : PlatformView, MethodChannel.MethodCallHandler, ZoomSDKAuthenticationListener {
  private val textView: TextView = TextView(context)
  private val methodChannel: MethodChannel = MethodChannel(messenger, "zoom_sdk/plugin")
  private val meetingStatusChannel: EventChannel = EventChannel(messenger, "zoom_sdk/meeting_status")

  init {
    methodChannel.setMethodCallHandler(this)
  }

  override fun onMethodCall(methodCall: MethodCall, result: MethodChannel.Result) {
    val meeting = ZoomMeeting(context, meetingStatusChannel)
    val arguments: Map<String, String> = methodCall.arguments()

    when (methodCall.method) {
      "init" -> meeting.init(arguments, result)
      "join" -> meeting.join(arguments, result)
      "start" -> meeting.start(arguments, result)
      "status" -> meeting.status(result)
      else -> result.notImplemented()
    }
  }

  override fun getView(): View {
    return textView
  }

  override fun dispose() {
  }

  override fun onZoomSDKLoginResult(result: Long) {
  }

  override fun onZoomSDKLogoutResult(result: Long) {
  }

  override fun onZoomIdentityExpired() {
  }

  override fun onZoomAuthIdentityExpired() {
  }
}
