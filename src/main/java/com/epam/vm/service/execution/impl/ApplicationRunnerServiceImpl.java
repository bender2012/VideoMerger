package com.epam.vm.service.execution.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.vm.service.execution.ApplicationRunnerService;
import com.epam.vm.service.settings.ApplicationConstants;

public class ApplicationRunnerServiceImpl implements ApplicationRunnerService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApplicationRunnerServiceImpl.class);
	private static final String PROCESS_EXIT_MESSAGE = "FFMPEG process exited with code: {}";

	@Override
	public void runApplication(String commandString) {
		BufferedReader inputInfoStream = null;
		BufferedReader inputErrorsStream = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(commandString);
			inputInfoStream = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			inputErrorsStream = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			String lineWithError = null;
			String lineWithInfo = null;
			while ((lineWithError = inputErrorsStream.readLine()) != null) {
				LOGGER.error(lineWithError);
			}
			while ((lineWithInfo = inputInfoStream.readLine()) != null) {
				LOGGER.info(lineWithInfo);
			}
			int exitVal = process.waitFor();
			LOGGER.info(PROCESS_EXIT_MESSAGE, exitVal);
		} catch (IOException e) {
			LOGGER.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
		} catch (IllegalThreadStateException e) {
			LOGGER.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
		} catch (InterruptedException e) {
			LOGGER.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
		} finally {
			if (inputInfoStream != null) {
				try {
					inputInfoStream.close();
				} catch (Exception e) {
					LOGGER.debug(
							ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
				}
			}
			if (inputErrorsStream != null) {
				try {
					inputErrorsStream.close();
				} catch (Exception e) {
					LOGGER.debug(
							ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
				}
			}
		}

	}
}
