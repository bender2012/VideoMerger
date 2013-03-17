package com.epam.vm.service.file.template;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface TemplateService {

	Map<String, String> getVSFilterPathTemplatePair();
	
	Map<String, String> getVideoAndSybtitleFilesTemplatepairs(File videoFile, File sybtitleFile);

	List<String> applyTemplates(List<String> textLines,
			Map<String, String> templatePairs);

}
