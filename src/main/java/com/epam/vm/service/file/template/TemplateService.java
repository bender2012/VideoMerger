package com.epam.vm.service.file.template;

import java.util.List;
import java.util.Map;

public interface TemplateService {

	Map<String, String> getAllTemplatePairs();

	List<String> applyTemplates(List<String> textLines,
			Map<String, String> templatePairs);

}
