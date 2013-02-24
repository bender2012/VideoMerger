package com.epam.vm.enums;

public enum ApplicationSetting {

	FFMPEG_COMMANDS("ffmpeg-commands"),
	FFMPEG_PATH("ffmpeg-path"),
	INPUT_FOLDERS("input-folders"),
	OUTPUT_FOLDER("output-folder"),
	VIDEO_FILE_EXTENTION("video-file-extention"),
	SYBTITLE_FILE_EXTENTION("sybtitle-file-extention");

	private String settingName;

	private ApplicationSetting(String settingName) {
		this.settingName = settingName;
	}

	public String getSettingName() {
		return this.settingName;
	}

}
