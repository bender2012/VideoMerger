package com.epam.vm.etc.filefilters;

import java.io.FilenameFilter;

public interface ExtentionFileFilterFactory {
	
	FilenameFilter getExtentionFileFilter(String extention);
	
	FilenameFilter getVideoFileFilter();
	
	FilenameFilter getSybtitlesFileFilter();

}
