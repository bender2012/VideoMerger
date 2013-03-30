package com.epam.vm.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.vm.converter.VideoConverterEngine;
import com.epam.vm.converter.impl.VideoConverterEngineImpl;

public final class Application {

	private static final  Logger LOGGER = LoggerFactory
			.getLogger(Application.class);
	private static final String CONVERTATION_SUCCESS_MESSAGE = "Convertation sucess: {}";
	
	private Application(){
		
	}

	public static void main(String[] args) {
		VideoConverterEngine videoConverterEngine = new VideoConverterEngineImpl();
		boolean isConvertationSucess = true;
		isConvertationSucess = videoConverterEngine.processVideo();
		LOGGER.info(CONVERTATION_SUCCESS_MESSAGE, isConvertationSucess);
	}

}
