package net.metainnovative.zoom_sdk

import io.flutter.plugin.common.EventChannel
import us.zoom.sdk.*

class ZoomMeetingStatusStreamHandler(private val meetingService: MeetingService) : EventChannel.StreamHandler {
  private var statusListener: MeetingServiceListener? = null

  override fun onListen(arguments: Any?, events: EventChannel.EventSink) {
    statusListener = MeetingServiceListener { meetingStatus, errorCode, internalErrorCode ->
      val response: List<String> = listOf(meetingStatus.name, errorCode.toString(), internalErrorCode.toString())

      events.success(response)
    }

    meetingService.addListener(statusListener)
  }

  override fun onCancel(arguments: Any?) {
    meetingService.removeListener(statusListener)
  }
}