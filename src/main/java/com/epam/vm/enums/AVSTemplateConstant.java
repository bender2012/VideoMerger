package com.epam.vm.enums;

public enum AVSTemplateConstant {
	
	FILTER_PATH("VS_FILTER_PATH"),
	INPUT_VIDEO_FILE("INPUT_VIDEO_FILE"),
	INPUT_SYBTITLES_FILE("INPUT_SYBTITLE_FILE");

	private String constantString;

	private AVSTemplateConstant(String constantString) {
		this.constantString = String.valueOf(constantString);
	}

	public String getStringValue() {
		return String.valueOf(this.constantString);
	}


}
