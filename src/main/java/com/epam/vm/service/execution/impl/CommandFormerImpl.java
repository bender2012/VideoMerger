package com.epam.vm.service.execution.impl;

import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.enums.FFMPEG_PARAMS;
import com.epam.vm.service.execution.CommandFormer;
import com.epam.vm.service.settings.PropertiesReader;
import com.epam.vm.service.settings.impl.PropertiesReaderImpl;

public class CommandFormerImpl implements CommandFormer {

	@Override
	public String getCommandFromApplicationSettings(String inputFilePath,
			String outputFilePath) {
		StringBuilder returnCommand = new StringBuilder();
		PropertiesReader properties = PropertiesReaderImpl.getInstance();
		returnCommand.append(properties
				.getPropertyValue(ApplicationSetting.FFMPEG_PATH));
		returnCommand.append(properties
				.getPropertyValue(ApplicationSetting.FFMPEG_APP_NAME));
		returnCommand.append(FFMPEG_PARAMS.DELIMITER.getStringValue());
		returnCommand.append(FFMPEG_PARAMS.INPUT_FILE.getStringValue());
		returnCommand.append(FFMPEG_PARAMS.DELIMITER.getStringValue());
		returnCommand.append(inputFilePath);
		returnCommand.append(FFMPEG_PARAMS.DELIMITER.getStringValue());
		returnCommand.append(properties
				.getPropertyValue(ApplicationSetting.FFMPEG_COMMANDS));
		returnCommand.append(FFMPEG_PARAMS.DELIMITER.getStringValue());
		returnCommand.append(outputFilePath);
		return returnCommand.toString();
	}

}
