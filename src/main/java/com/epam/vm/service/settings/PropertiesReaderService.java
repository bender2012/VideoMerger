package com.epam.vm.service.settings;

import com.epam.vm.enums.ApplicationSetting;

public interface PropertiesReaderService {

	String getPropertyValue(ApplicationSetting setting);

}
