package com.epam.vm.service.file.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.etc.filefilters.ExtentionFileFilterFactory;
import com.epam.vm.etc.filefilters.impl.ExtentionFileFilterFactoryImpl;
import com.epam.vm.exceptions.file.FileException;
import com.epam.vm.exceptions.file.filename.WrongExtentionLengthException;
import com.epam.vm.exceptions.file.filename.WrongFileNameLengthException;
import com.epam.vm.exceptions.folder.NotFolderException;
import com.epam.vm.service.file.FileService;
import com.epam.vm.service.settings.ApplicationConstants;
import com.epam.vm.service.settings.impl.PropertiesReaderServiceImpl;

public class FileServiceImpl implements FileService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileServiceImpl.class);

	private static final String FOLDER_LIST_SEPARATOR = ";";
	private static final String ILLEGALE_ARGUMENT_TEMPLATE = "Illegale input argument";
	private static final String WRONG_FILENAME_LENGTH = "Wrong filename length: {}";
	private static final String WRONG_EXTENTION_LENGTH = "Wrong extention length: {}";
	private static final String ERROR_CLOSING_OUTPUT_STREAM_TEMPLATE = "Error while closing output stream";
	private static final String ERROR_WHILE_WRITING_TO_AVS_FILE = "Error writing to avs file";
	private static final String ERROR_CREATING_AVS_FILE_TEMPLATE = "Error creating avs script file";
	private static final String FILE_NOT_FOUND = "Template file not found. Path: {}";
	private static final String ERROR_READING_FILE = "Error reading file. File path: {}";
	private static final String ERROR_CLOASING_READER = "Error cloasing file reader. File path: {}";

	private static final String WRONG_FILE_NAME = "Wrong file name: {}";

	@Override
	public List<File> getInputFolderList(String inputFolders) {
		List<File> inputFolderList = new ArrayList<File>();
		List<String> inputFoldersNames = Arrays.asList(inputFolders
				.split(FOLDER_LIST_SEPARATOR));
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
			throw new NotFolderException(inputFolder.getAbsolutePath());
		}
		ExtentionFileFilterFactory extentionFileFilterFactory = new ExtentionFileFilterFactoryImpl();
		FilenameFilter videoFileNameFilter = extentionFileFilterFactory
				.getVideoFileFilter();
		FilenameFilter sybtitleFileNameFilter = extentionFileFilterFactory
				.getSybtitlesFileFilter();
		List<File> videoFilesList = Arrays.asList(inputFolder
				.listFiles(videoFileNameFilter));
		List<File> sybtitleFilesList = Arrays.asList(inputFolder
				.listFiles(sybtitleFileNameFilter));
		String videoFileNameVithoutExtention = null;
		String sybtitleFileNameVithoutExtention = null;
		String videoFileExtention = PropertiesReaderServiceImpl
				.getInstance()
				.getPropertyValue(ApplicationSetting.INPUT_VIDEO_FILE_EXTENTION);
		String sybtitleFileExtention = PropertiesReaderServiceImpl
				.getInstance().getPropertyValue(
						ApplicationSetting.SYBTITLE_FILE_EXTENTION);
		for (File videoFile : videoFilesList) {
			for (File sybtitleFile : sybtitleFilesList) {
				try {
					videoFileNameVithoutExtention = getFileNameWithoutExtention(
							videoFile.getName(), videoFileExtention);
					sybtitleFileNameVithoutExtention = getFileNameWithoutExtention(
							sybtitleFile.getName(), sybtitleFileExtention);
					if (videoFileNameVithoutExtention
							.equals(sybtitleFileNameVithoutExtention)) {
						filePairs.put(videoFile, sybtitleFile);
					}
				} catch (IllegalArgumentException e) {
					LOGGER.debug(ILLEGALE_ARGUMENT_TEMPLATE);
					LOGGER.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE,
							e);
				} catch (WrongFileNameLengthException e) {
					LOGGER.debug(WRONG_FILENAME_LENGTH, e.getMessage());
					LOGGER.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE,
							e);
				} catch (WrongExtentionLengthException e) {
					LOGGER.debug(WRONG_EXTENTION_LENGTH, e.getMessage());
					LOGGER.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE,
							e);
				} catch (FileException e) {
					LOGGER.debug(WRONG_FILE_NAME, e.getMessage());
					LOGGER.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE,
							e);
				}
			}
		}

		return filePairs;
	}

	@Override
	public String getFileNameWithoutExtention(String fileName, String extention)
			throws FileException {
		String returnFileName = null;
		int fileNameLength = fileName.length();
		int extentionLength = extention.length();
		if (fileNameLength <= 0) {
			throw new WrongFileNameLengthException(fileName);
		}
		if (extentionLength <= 0) {
			throw new WrongExtentionLengthException(extention);
		}
		if ((fileNameLength - extentionLength) <= 1) {
			throw new IllegalArgumentException();
		}
		returnFileName = String.copyValueOf(fileName.toCharArray(), 0,
				fileNameLength - extentionLength);
		return returnFileName;
	}

	@Override
	public File formAvsScriptFile(String filePath, List<String> scriptLines) {
		File returnFile = new File(filePath);

		try {
			returnFile.createNewFile();
		} catch (IOException e) {
			LOGGER.debug(ERROR_CREATING_AVS_FILE_TEMPLATE);
			LOGGER.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
		}

		BufferedWriter outputStreamWriter = null;
		try {
			outputStreamWriter = new BufferedWriter(new FileWriter(returnFile));
			for (String line : scriptLines) {
				outputStreamWriter.write(line);
				outputStreamWriter.newLine();
			}
		} catch (IOException e) {
			LOGGER.debug(ERROR_WHILE_WRITING_TO_AVS_FILE);
			LOGGER.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
		} finally {
			if (outputStreamWriter != null) {
				try {
					outputStreamWriter.close();
				} catch (Exception e) {
					LOGGER.debug(ERROR_CLOSING_OUTPUT_STREAM_TEMPLATE);
					LOGGER.debug(
							ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
				}
			}
		}
		return returnFile;
	}

	@Override
	public List<String> getTextFileLines(String filePath) {
		List<String> fileLines = new ArrayList<String>();
		File avsTemplateFile = null;
		avsTemplateFile = new File(filePath);
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(avsTemplateFile));
			String fileLine = null;
			while ((fileLine = bufferedReader.readLine()) != null) {
				fileLines.add(fileLine);
			}
		} catch (FileNotFoundException e) {
			LOGGER.debug(FILE_NOT_FOUND, avsTemplateFile);
			LOGGER.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
		} catch (IOException e) {
			LOGGER.debug(ERROR_READING_FILE, filePath);
			LOGGER.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception e) {
				LOGGER.debug(ERROR_CLOASING_READER, filePath);
				LOGGER.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
			}
		}
		return fileLines;
	}

}
