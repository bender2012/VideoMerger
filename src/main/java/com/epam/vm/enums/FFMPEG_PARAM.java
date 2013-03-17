package com.epam.vm.enums;

public enum FFMPEG_PARAM {

	INPUT_FILE("-i"),
	SAME_QUOLITY("-qscale 0"),
	SINGLE_QUOTES("\""),
	DELIMITER(" ");

	private String commandString;

	private FFMPEG_PARAM(String commandString) {
		this.commandString = String.valueOf(commandString);
	}

	public String getStringValue() {
		return String.valueOf(this.commandString);
	}

}
