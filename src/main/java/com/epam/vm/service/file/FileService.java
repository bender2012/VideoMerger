package com.epam.vm.service.file;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.epam.vm.exceptions.file.FileException;
import com.epam.vm.exceptions.folder.NotFolderException;

public interface FileService {

	List<File> getInputFolderList(String inputFolders);

	// Video file : Sysbtitle file
	Map<File, File> getVideoWithSubtitlesPairs(File inputFolder)
			throws NotFolderException;

	String getFileNameWithoutExtention(String fileName, String extention)
			throws FileException;

	File formAvsScriptFile(String filePath, List<String> scriptLines);

	List<String> getTextFileLines(String filePath);

}
