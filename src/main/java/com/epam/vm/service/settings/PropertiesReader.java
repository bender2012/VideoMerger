package com.epam.vm.service.settings;

import com.epam.vm.enums.ApplicationSetting;

public interface PropertiesReader {

	String getPropertyValue(ApplicationSetting setting);

}
