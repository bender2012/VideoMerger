package com.epam.vm.settings;

import com.epam.vm.enums.ApplicationSetting;

public interface PropertiesReader {

	String getPropertyValue(ApplicationSetting setting);

}