package com.epam.vm.etc.filefilters.impl;

import java.io.File;
import java.io.FilenameFilter;

import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.etc.filefilters.ExtentionFileFilterFactory;
import com.epam.vm.service.settings.PropertiesReader;
import com.epam.vm.service.settings.impl.PropertiesReaderImpl;

public class ExtentionFileFilterFactoryImpl implements
		ExtentionFileFilterFactory {

	@Override
	public FilenameFilter getExtentionFileFilter(String extention) {
		final String fileExtention = "." + extention;
		FilenameFilter fileFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(fileExtention);
			}
		};
		return fileFilter;
	}

	@Override
	public FilenameFilter getVideoFileFilter() {
		PropertiesReader propertiesReader = PropertiesReaderImpl.getInstance();
		return getExtentionFileFilter(propertiesReader
				.getPropertyValue(ApplicationSetting.VIDEO_FILE_EXTENTION));
	}

	@Override
	public FilenameFilter getSybtitlesFileFilter() {
		PropertiesReader propertiesReader = PropertiesReaderImpl.getInstance();
		return getExtentionFileFilter(propertiesReader
				.getPropertyValue(ApplicationSetting.SYBTITLE_FILE_EXTENTION));
	}

}
