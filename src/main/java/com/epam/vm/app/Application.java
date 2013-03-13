package com.epam.vm.app;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.exceptions.NotFolderException;
import com.epam.vm.service.execution.CommandFormerService;
import com.epam.vm.service.execution.impl.CommandFormerServiceImpl;
import com.epam.vm.service.file.FileService;
import com.epam.vm.service.file.avs.AvsScriptService;
import com.epam.vm.service.file.avs.impl.AvsScriptServiceImpl;
import com.epam.vm.service.file.impl.FileServiceImpl;
import com.epam.vm.service.settings.PropertiesReaderService;
import com.epam.vm.service.settings.impl.PropertiesReaderServiceImpl;


public class Application {

	public Application() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		PropertiesReaderService properties = PropertiesReaderServiceImpl.getInstance();
//		System.out.println(properties.getPropertyValue(ApplicationSetting.FFMPEG_COMMANDS));
//		System.out.println(properties.getPropertyValue(ApplicationSetting.INPUT_FOLDERS));
//		System.out.println(properties.getPropertyValue(ApplicationSetting.OUTPUT_FOLDER));
		FileService fileService = new FileServiceImpl();
		List<File> foldersList = fileService.getInputFolderList(properties.getPropertyValue(ApplicationSetting.INPUT_FOLDERS));
		for (File file : foldersList) {
			System.out.println(file.getPath());
			try {
				fileService.getVideoWithSubtitlesPairs(file);
			} catch (NotFolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		CommandFormerService cf = new CommandFormerServiceImpl();
		System.out.println(cf.getCommandFromApplicationSettings("D:\\tmp\\a.avs", "D:\\tmp\\01_05_.wmv"));
		
		AvsScriptService a = new AvsScriptServiceImpl();
		
		/*
		LoadPlugin("$VS_FILTER_PATH$")
		AviSource("$INPUT_VIDEO_FILE$")
		TextSub("$INPUT_SYBTITLE_FILE$")
		*/
		Map<String, String> templatePairs = new HashMap<String, String>();
		templatePairs.put("VS_FILTER_PATH", "filter-path");
		templatePairs.put("INPUT_VIDEO_FILE", "input-video-file");
		templatePairs.put("INPUT_SYBTITLE_FILE", "input-sybtitle-file");
		
		a.getScriptCommandsFromTemplate(templatePairs);
		//a.getScriptCommandsFromTemplate();
		
	}
	
	

}
