package com.epam.vm.etc.filefilters.impl;

import java.io.File;
import java.io.FilenameFilter;

import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.etc.filefilters.ExtentionFileFilterFactory;
import com.epam.vm.service.settings.PropertiesReaderService;
import com.epam.vm.service.settings.impl.PropertiesReaderServiceImpl;

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
		PropertiesReaderService propertiesReader = PropertiesReaderServiceImpl.getInstance();
		return getExtentionFileFilter(propertiesReader
				.getPropertyValue(ApplicationSetting.INPUT_VIDEO_FILE_EXTENTION));
	}

	@Override
	public FilenameFilter getSybtitlesFileFilter() {
		PropertiesReaderService propertiesReader = PropertiesReaderServiceImpl.getInstance();
		return getExtentionFileFilter(propertiesReader
				.getPropertyValue(ApplicationSetting.SYBTITLE_FILE_EXTENTION));
	}

}
