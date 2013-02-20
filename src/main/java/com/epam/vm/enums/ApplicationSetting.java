package com.epam.vm.enums;

public enum ApplicationSetting {

	FFMPEG_COMMANDS("ffmpeg-commands");

	private String settingName;

	private ApplicationSetting(String settingName) {
		this.settingName = settingName;
	}

	public String getSettingName() {
		return this.settingName;
	}

}
