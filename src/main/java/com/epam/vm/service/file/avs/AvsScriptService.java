package com.epam.vm.service.file.avs;

import java.util.List;
import java.util.Map;

public interface AvsScriptService {
	
	List<String> getScriptCommandsFromTemplate(Map<String, String> templatePairs);

}
