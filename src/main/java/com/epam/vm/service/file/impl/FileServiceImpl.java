package com.epam.vm.service.file.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.vm.exceptions.NotFolderException;
import com.epam.vm.service.file.FileService;

public class FileServiceImpl implements FileService {

	private final static String folderListSeparator = ";";

	@Override
	public List<File> getInputFolderList(String inputFolders) {
		List<File> inputFolderList = new ArrayList<File>();
		List<String> inputFoldersNames = Arrays.asList(inputFolders
				.split(folderListSeparator));
		for (String string : inputFoldersNames) {
			inputFolderList.add(new File(string));
		}
		return inputFolderList;
	}

	@Override
	public Map<File, File> getVideoWithSubtitlesPairs(File inputFolder)
			throws NotFolderException {
		Map<File, File> filePairs = new HashMap<File, File>();
		if (!inputFolder.isDirectory()) {
			throw new NotFolderException();
		} else {
			
			//inputFolder.list(
		}

		return filePairs;
	}

}
