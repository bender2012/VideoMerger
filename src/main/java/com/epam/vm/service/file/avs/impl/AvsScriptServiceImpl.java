package com.epam.vm.service.file.avs.impl;

import java.util.List;
import java.util.Map;

import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.service.file.FileService;
import com.epam.vm.service.file.avs.AvsScriptService;
import com.epam.vm.service.file.impl.FileServiceImpl;
import com.epam.vm.service.file.template.TemplateService;
import com.epam.vm.service.file.template.impl.TemplateServiceImpl;
import com.epam.vm.service.settings.PropertiesReaderService;
import com.epam.vm.service.settings.impl.PropertiesReaderServiceImpl;

public class AvsScriptServiceImpl implements AvsScriptService {

	@Override
	public List<String> getScriptCommandsFromTemplate(
			Map<String, String> templatePairs) {
		FileService fileService = new FileServiceImpl();
		List<String> returnList = null;
		List<String> avsTemplateFileLines = null;
		PropertiesReaderService applicationProperties = PropertiesReaderServiceImpl
				.getInstance();
		TemplateService templateService = new TemplateServiceImpl();
		avsTemplateFileLines = fileService
				.getTextFileLines(applicationProperties
						.getPropertyValue(ApplicationSetting.AVS_SCRIPT_TEMPLATE_FILE_NAME));
		returnList = templateService.applyTemplates(avsTemplateFileLines,
				templatePairs);
		return returnList;
	}

}
