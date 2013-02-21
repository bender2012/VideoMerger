package com.epam.vm.service.file;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.epam.vm.exceptions.NotFolderException;

public interface FileService {
	
	List<File> getInputFolderList(String inputFolders);
	
	Map<File, File> getVideoWithSubtitlesPairs(File inputFolder) throws NotFolderException;
		
}
