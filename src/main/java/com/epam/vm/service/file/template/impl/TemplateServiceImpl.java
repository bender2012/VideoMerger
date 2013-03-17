package com.epam.vm.service.file.template.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

import com.epam.vm.enums.AVSTemplateConstant;
import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.service.file.template.TemplateService;
import com.epam.vm.service.settings.PropertiesReaderService;
import com.epam.vm.service.settings.impl.PropertiesReaderServiceImpl;

public class TemplateServiceImpl implements TemplateService {

	@Override
	public List<String> applyTemplates(List<String> textLines,
			Map<String, String> templatePairs) {
		List<String> returnLines = new ArrayList<String>();
		StringTemplate temporaryStringTemplate = null;
		for (String line : textLines) {
			temporaryStringTemplate = new StringTemplate(line,
					DefaultTemplateLexer.class);
			for (Entry<String, String> templatePair : templatePairs.entrySet()) {
				if (line.contains(templatePair.getKey())) {
					temporaryStringTemplate.setAttribute(templatePair.getKey(),
							templatePair.getValue());
					break;
				}
			}
			returnLines.add(String.valueOf(temporaryStringTemplate.toString()));
		}
		return returnLines;
	}

	@Override
	public Map<String, String> getVSFilterPathTemplatePair() {
		PropertiesReaderService properties = PropertiesReaderServiceImpl
				.getInstance();
		Map<String, String> templatePair = new HashMap<String, String>();
		templatePair.put(AVSTemplateConstant.FILTER_PATH.getStringValue(),
				properties.getPropertyValue(ApplicationSetting.VS_FILTER_PATH));
		return templatePair;
	}

	@Override
	public Map<String, String> getVideoAndSybtitleFilesTemplatepairs(
			File videoFile, File sybtitleFile) {
		Map<String, String> templatePairs = new HashMap<String, String>();
		templatePairs.put(
				AVSTemplateConstant.INPUT_VIDEO_FILE.getStringValue(),
				videoFile.getAbsolutePath());
		templatePairs.put(
				AVSTemplateConstant.INPUT_SYBTITLES_FILE.getStringValue(),
				sybtitleFile.getAbsolutePath());
		return templatePairs;
	}
}
