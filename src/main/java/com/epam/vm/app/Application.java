package com.epam.vm.app;

import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.service.file.FileService;
import com.epam.vm.service.file.impl.FileServiceImpl;
import com.epam.vm.service.settings.PropertiesReader;
import com.epam.vm.service.settings.impl.PropertiesReaderImpl;


public class Application {

	public Application() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PropertiesReader properties = new PropertiesReaderImpl();
//		System.out.println(properties.getPropertyValue(ApplicationSetting.FFMPEG_COMMANDS));
//		System.out.println(properties.getPropertyValue(ApplicationSetting.INPUT_FOLDERS));
//		System.out.println(properties.getPropertyValue(ApplicationSetting.OUTPUT_FOLDER));
		FileService fileService = new FileServiceImpl();
		fileService.getInputFolderList(properties.getPropertyValue(ApplicationSetting.INPUT_FOLDERS));
	}

}
