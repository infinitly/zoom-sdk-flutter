class ZoomStartMeetingOptions {
  String userId;
  String displayName;
  String meetingId;
  String meetingPassword;
  String zoomAccessToken;
  String disableDialIn;
  String disableDrive;
  String disableInvite;
  String disableShare;
  String noDisconnectAudio;
  String noAudio;

  ZoomStartMeetingOptions(
      {this.userId,
      this.displayName,
      this.meetingId,
      this.meetingPassword,
      this.zoomAccessToken,
      this.disableDialIn,
      this.disableDrive,
      this.disableInvite,
      this.disableShare,
      this.noDisconnectAudio,
      this.noAudio});
}
