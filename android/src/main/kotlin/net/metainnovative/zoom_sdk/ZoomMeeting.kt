package net.metainnovative.zoom_sdk

import android.content.Context
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import us.zoom.sdk.*

class ZoomMeeting(private val context: Context, private val meetingStatusChannel: EventChannel) {
  fun init(options: Map<String, String>, result: MethodChannel.Result) {
    val zoomSDK: ZoomSDK = ZoomSDK.getInstance()

    if (zoomSDK.isInitialized) {
      val response: List<Int> = listOf(0, 0)

      result.success(response)

      return
    }

    val initParams = ZoomSDKInitParams()
    initParams.jwtToken = options["jwtToken"]
    initParams.appKey = options["appKey"]
    initParams.appSecret = options["appSecret"]
    initParams.domain = options["domain"]

    zoomSDK.initialize(
      context,
      object : ZoomSDKInitializeListener {
        override fun onZoomAuthIdentityExpired() {
        }

        override fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) {
          val response: List<Int> = listOf(errorCode, internalErrorCode)

          if (errorCode == ZoomError.ZOOM_ERROR_SUCCESS) {
            val meetingService: MeetingService = zoomSDK.meetingService

            meetingStatusChannel.setStreamHandler(ZoomMeetingStatusStreamHandler(meetingService))
          } else {
            println("Failed to initialize Zoom SDK")
          }

          result.success(response)
        }
      },
      initParams
    )
  }

  fun join(arguments: Map<String, String>, result: MethodChannel.Result) {
    val zoomSDK: ZoomSDK = ZoomSDK.getInstance()
    val meetingService: MeetingService = zoomSDK.meetingService ?: return result.success(false)

    val options = JoinMeetingOptions()
    options.no_invite = parseBoolean(arguments["disableInvite"], false)
    options.no_share = parseBoolean(arguments["disableShare"], false)
    options.no_driving_mode = parseBoolean(arguments["disableDrive"], false)
    options.no_dial_in_via_phone = parseBoolean(arguments["disableDialIn"], false)
    options.no_disconnect_audio = parseBoolean(arguments["noDisconnectAudio"], false)
    options.no_audio = parseBoolean(arguments["noAudio"], false)

    val params = JoinMeetingParams()
    params.displayName = arguments["displayName"]
    params.meetingNo = arguments["meetingId"]
    params.password = arguments["meetingPassword"]

    meetingService.joinMeetingWithParams(context, params, options)
    result.success(true)
  }

  fun start(arguments: Map<String, String>, result: MethodChannel.Result) {
    val zoomSDK: ZoomSDK = ZoomSDK.getInstance()
    val meetingService: MeetingService = zoomSDK.meetingService ?: return result.success(false)

    val options = StartMeetingOptions()
    options.no_invite = parseBoolean(arguments["disableInvite"], false)
    options.no_share = parseBoolean(arguments["disableShare"], false)
    options.no_driving_mode = parseBoolean(arguments["disableDrive"], false)
    options.no_dial_in_via_phone = parseBoolean(arguments["disableDialIn"], false)
    options.no_disconnect_audio = parseBoolean(arguments["noDisconnectAudio"], false)
    options.no_audio = parseBoolean(arguments["noAudio"], false)

    val params = StartMeetingParamsWithoutLogin()
    params.userId = arguments["userId"]
    params.displayName = arguments["displayName"]
    params.meetingNo = arguments["meetingId"]
    params.userType = MeetingService.USER_TYPE_API_USER
    params.zoomAccessToken = arguments["zoomAccessToken"]

    meetingService.startMeetingWithParams(context, params, options)
    result.success(true)
  }

  fun status(result: MethodChannel.Result) {
    val zoomSDK: ZoomSDK = ZoomSDK.getInstance()
    val meetingService: MeetingService = zoomSDK.meetingService ?: return result.success("MEETING_STATUS_UNKNOWN")
    val meetingStatus = meetingService.meetingStatus

    result.success(meetingStatus?.name ?: "MEETING_STATUS_UNKNOWN")
  }

  private fun parseBoolean(argument: String?, defaultValue: Boolean): Boolean {
    return argument?.toBoolean() ?: defaultValue
  }
}