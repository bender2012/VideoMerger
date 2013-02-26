package com.epam.vm.service.file.avs.impl;

import java.util.List;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

import com.epam.vm.service.file.avs.AvsScriptService;



public class AvsScriptServiceImpl implements AvsScriptService {

	

	@Override
	public List<String> getScriptCommandsFromTemplate() {
		List<String> returnList = null;
		StringTemplate hello = new StringTemplate("Hello, $name$", DefaultTemplateLexer.class);
		hello.setAttribute("name", "World");
		System.out.println(hello.toString());

		return returnList;
	}

}
