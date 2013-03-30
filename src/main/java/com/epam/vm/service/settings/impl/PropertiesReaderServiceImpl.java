package com.epam.vm.service.settings.impl;

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
import com.epam.vm.service.settings.ApplicationConstants;
import com.epam.vm.service.settings.PropertiesReaderService;

public final class PropertiesReaderServiceImpl implements PropertiesReaderService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PropertiesReaderServiceImpl.class);
	private static final String EXCEPTION_LOG_STRING = "Exception: {}";
	private static final String FILE_NOT_FOUND_LOG_STRING = "File not found: {}";
	private static final String START_READ_PROPERTIES = "Starting read properties file: {}";
	private static final String PROPERTY_TEMPLATE = "{} {}";
	private static PropertiesReaderService instance;
	private static Map<ApplicationSetting, String> propertiesMap;

	private PropertiesReaderServiceImpl() {
		super();
		propertiesMap = null;
	}

	public static PropertiesReaderService getInstance() {
		if (instance == null) {
			instance = new PropertiesReaderServiceImpl();
		}
		return instance;
	}

	private static void init() {
		propertiesMap = new HashMap<ApplicationSetting, String>();
		String applicationPath = PropertiesReaderServiceImpl.class
				.getProtectionDomain().getCodeSource().getLocation().getPath();
		File temporaryFile = new File(applicationPath);
		String parrentFolder = temporaryFile.getParent();
		StringBuilder propertiesFileName = new StringBuilder();
		propertiesFileName.append(parrentFolder);
		propertiesFileName.append(File.separator);
		propertiesFileName.append(ApplicationConstants.PROPERTIES_FILE_NAME);
		Properties applicatioProperties = new Properties();
		LOGGER.info(START_READ_PROPERTIES, propertiesFileName);
		try {
			applicatioProperties.load(new FileInputStream(new File(
					propertiesFileName.toString())));
			for (ApplicationSetting applicationSetting : ApplicationSetting
					.values()) {
				LOGGER.info(PROPERTY_TEMPLATE, applicationSetting
						.getSettingName(), applicatioProperties
						.getProperty(applicationSetting.getSettingName()));
				propertiesMap.put(applicationSetting, applicatioProperties
						.getProperty(applicationSetting.getSettingName()));
			}
		} catch (FileNotFoundException e) {
			LOGGER.info(FILE_NOT_FOUND_LOG_STRING, propertiesFileName);
			LOGGER.info(EXCEPTION_LOG_STRING, e);
		} catch (IOException e) {
			LOGGER.info(EXCEPTION_LOG_STRING, e);
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
