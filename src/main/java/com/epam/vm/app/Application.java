package com.epam.vm.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.vm.converter.VideoConverterEngine;
import com.epam.vm.converter.impl.VideoConverterEngineImpl;

public class Application {

	private final static Logger logger = LoggerFactory
			.getLogger(Application.class);
	private static final String CONVERTATION_SUCCESS_MESSAGE = "Convertation sucess: {}";

	public static void main(String[] args) {
		VideoConverterEngine videoConverterEngine = new VideoConverterEngineImpl();
		boolean isConvertationSucess = true;
		isConvertationSucess = videoConverterEngine.processVideo();
		logger.info(CONVERTATION_SUCCESS_MESSAGE, isConvertationSucess);
	}

}
