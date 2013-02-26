package com.epam.vm.service.execution.impl;

import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.enums.FFMPEG_PARAM;
import com.epam.vm.service.execution.CommandFormerService;
import com.epam.vm.service.settings.PropertiesReaderService;
import com.epam.vm.service.settings.impl.PropertiesReaderServiceImpl;

public class CommandFormerServiceImpl implements CommandFormerService {

	@Override
	public String getCommandFromApplicationSettings(String inputFilePath,
			String outputFilePath) {
		StringBuilder returnCommand = new StringBuilder();
		PropertiesReaderService properties = PropertiesReaderServiceImpl.getInstance();
		returnCommand.append(properties
				.getPropertyValue(ApplicationSetting.FFMPEG_PATH));
		returnCommand.append(properties
				.getPropertyValue(ApplicationSetting.FFMPEG_APP_NAME));
		returnCommand.append(FFMPEG_PARAM.DELIMITER.getStringValue());
		returnCommand.append(FFMPEG_PARAM.INPUT_FILE.getStringValue());
		returnCommand.append(FFMPEG_PARAM.DELIMITER.getStringValue());
		returnCommand.append(inputFilePath);
		returnCommand.append(FFMPEG_PARAM.DELIMITER.getStringValue());
		returnCommand.append(properties
				.getPropertyValue(ApplicationSetting.FFMPEG_COMMANDS));
		returnCommand.append(FFMPEG_PARAM.DELIMITER.getStringValue());
		returnCommand.append(outputFilePath);
		return returnCommand.toString();
	}

}
