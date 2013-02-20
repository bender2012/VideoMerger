package com.epam.vm.app;

import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.settings.PropertiesReader;
import com.epam.vm.settings.impl.PropertiesReaderImpl;


public class Application {

	public Application() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PropertiesReader properties = new PropertiesReaderImpl();
		System.out.println(properties.getPropertyValue(ApplicationSetting.FFMPEG_COMMANDS));
		
	}

}
