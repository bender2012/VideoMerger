package com.epam.vm.service.file.avs.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.service.file.FileService;
import com.epam.vm.service.file.avs.AvsScriptService;
import com.epam.vm.service.file.impl.FileServiceImpl;
import com.epam.vm.service.file.template.TemplateService;
import com.epam.vm.service.file.template.impl.TemplateServiceImpl;
import com.epam.vm.service.settings.ApplicationConstants;
import com.epam.vm.service.settings.PropertiesReaderService;
import com.epam.vm.service.settings.impl.PropertiesReaderServiceImpl;



public class AvsScriptServiceImpl implements AvsScriptService {

	

	@Override
	public List<String> getScriptCommandsFromTemplate(Map<String, String> templatePairs) {
		FileService fileService = new FileServiceImpl();
		List<String> returnList = null;
		List<String> avsTemplateFileLines = null;
		
		PropertiesReaderService applicationProperties = PropertiesReaderServiceImpl.getInstance();
		
		//avsTemplateFileLines = fileService.getTextFileLines(applicationProperties.getPropertyValue(ApplicationSetting.));
		
		
		TemplateService templateService = new TemplateServiceImpl();
		
		avsTemplateFileLines = fileService.getTextFileLines(applicationProperties.getPropertyValue(ApplicationSetting.AVS_SCRIPT_TEMPLATE_FILE_NAME));
		

		
		returnList = templateService.applyTemplates(avsTemplateFileLines, templatePairs);

//		System.out.println("avs file:");
//		for (String string : returnList) {
//			System.out.println(string);
//		}
		
		//apply template
		
		
//		StringTemplate hello = new StringTemplate("Hello, $name$", DefaultTemplateLexer.class);
//		hello.setAttribute("name", "World");
//		System.out.println(hello.toString());

		return returnList;
	}

}
