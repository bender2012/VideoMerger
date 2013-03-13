package com.epam.vm.service.file.template.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

import com.epam.vm.service.file.template.TemplateService;

public class TemplateServiceImpl implements TemplateService {

	@Override
	public Map<String, String> getAllTemplatePairs() {
		Map<String, String> m;
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> applyTemplates(List<String> textLines,
			Map<String, String> templatePairs) {		
		List<String> returnLines = new ArrayList<String>();		
		StringTemplate temporaryStringTemplate = null;		
		for (String line : textLines) {
			temporaryStringTemplate = new StringTemplate(line, DefaultTemplateLexer.class);			
			for (Entry<String, String> templatePair : templatePairs.entrySet()) {
				if(line.contains(templatePair.getKey())){					
					temporaryStringTemplate.setAttribute(templatePair.getKey(), templatePair.getValue());					
					break;
				}
			}
			returnLines.add(String.valueOf(temporaryStringTemplate.toString()));			
		}
		return returnLines;
	}
}
