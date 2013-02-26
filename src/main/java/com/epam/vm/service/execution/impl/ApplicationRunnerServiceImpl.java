package com.epam.vm.service.execution.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.vm.service.execution.ApplicationRunnerService;
import com.epam.vm.service.settings.ApplicationConstants;

public class ApplicationRunnerServiceImpl implements ApplicationRunnerService {

	private static final Logger debugLogger = LoggerFactory
			.getLogger(ApplicationRunnerServiceImpl.class);

	@Override
	public void runApplication(String commandString) {

		Runtime runtime = Runtime.getRuntime();
		try {
			Process applicationProcess = runtime.exec(commandString);
			applicationProcess.exitValue();
		} catch (IOException e) {
			debugLogger.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
		}

	}
}
