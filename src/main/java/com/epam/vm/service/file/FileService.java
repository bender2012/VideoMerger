package com.epam.vm.service.file;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.epam.vm.exceptions.NotFolderException;
import com.epam.vm.exceptions.WrongExtentionLengthException;
import com.epam.vm.exceptions.WrongFileNameLengthException;

public interface FileService {

	List<File> getInputFolderList(String inputFolders);

	//Video file : Sysbtitle file
	Map<File, File> getVideoWithSubtitlesPairs(File inputFolder)
			throws NotFolderException;

	String getFileNameWithoutExtention(String fileName, String extention)
			throws WrongFileNameLengthException, WrongExtentionLengthException,
			IllegalArgumentException;

}
