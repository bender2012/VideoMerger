package com.epam.vm.enums;

public enum ApplicationSetting {

	FFMPEG_APP_NAME("ffmpeg-application-name"),
	FFMPEG_COMMANDS("ffmpeg-commands"),
	FFMPEG_PATH("ffmpeg-path"),
	INPUT_FOLDERS("input-folders"),
	OUTPUT_FOLDER("output-folder"),
	INPUT_VIDEO_FILE_EXTENTION("input-video-file-extention"),
	OUTPUT_VIDEO_FILE_EXTENTION("output-video-file-extention"),
	SYBTITLE_FILE_EXTENTION("sybtitle-file-extention"),
	AVS_SCRIPT_TEMPLATE_FILE_NAME("avs-script-template-filename"),
	AVS_TEMPORARY_SCRIPT_FILE_NAME("avs-temporary-script-filename"),
	VS_FILTER_PATH("vs-filter-path");	

	private String settingName;

	private ApplicationSetting(String settingName) {
		this.settingName = String.valueOf(settingName);
	}

	public String getSettingName() {
		return String.valueOf(this.settingName);
	}

}
