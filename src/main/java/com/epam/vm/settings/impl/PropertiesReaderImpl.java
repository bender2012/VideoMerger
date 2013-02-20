package com.epam.vm.settings.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.settings.ApplicationConstants;
import com.epam.vm.settings.PropertiesReader;

public class PropertiesReaderImpl implements PropertiesReader {

	private static final Logger logger = LoggerFactory
			.getLogger(PropertiesReaderImpl.class);

	private static Map<ApplicationSetting, String> propertiesMap;

	public PropertiesReaderImpl() {
		super();
		propertiesMap = null;
	}

	private static void init() {
		propertiesMap = new HashMap<ApplicationSetting, String>();
		

		String applicationPath = PropertiesReaderImpl.class
				.getProtectionDomain().getCodeSource().getLocation().getPath();
		File temporaryFile = new File(applicationPath);
		String parrentFolder = temporaryFile.getParent();
		StringBuilder propertiesFileName = new StringBuilder();
		propertiesFileName.append(parrentFolder);
		propertiesFileName.append(File.separator);
		propertiesFileName.append(ApplicationConstants.propertiesFileName);
		Properties applicatioProperties = new Properties();
		try {
			applicatioProperties.load(new FileInputStream(new File(
					propertiesFileName.toString())));
			
			
			propertiesMap.put(ApplicationSetting.FFMPEG_COMMANDS, applicatioProperties.getProperty(ApplicationSetting.FFMPEG_COMMANDS.getSettingName()));
		} catch (FileNotFoundException e) {
			logger.info("File not found: " + propertiesFileName);
			logger.info("{}", e);			
		} catch (IOException e) {
			logger.info("{}", e);			
		}

	}

	@Override
	public String getPropertyValue(ApplicationSetting setting) {
		if (propertiesMap == null) {
			init();
		}
		String returnValue = null;
		returnValue = propertiesMap.get(setting);
		return returnValue;
	}

}
