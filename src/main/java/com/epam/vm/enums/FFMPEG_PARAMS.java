package com.epam.vm.enums;

public enum FFMPEG_PARAMS {

	INPUT_FILE("-i"),
	SAME_QUOLITY("-qscale 0"),
	DELIMITER(" ");

	private String commandString;

	private FFMPEG_PARAMS(String commandString) {
		this.commandString = String.valueOf(commandString);
	}

	public String getStringValue() {
		return String.valueOf(this.commandString);
	}

}
