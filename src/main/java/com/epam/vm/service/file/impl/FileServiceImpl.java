package com.epam.vm.service.file.impl;

import java.io.File;
import java.io.FilenameFilter;
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
import com.epam.vm.exceptions.NotFolderException;
import com.epam.vm.exceptions.WrongExtentionLength;
import com.epam.vm.exceptions.WrongFileNameLength;
import com.epam.vm.service.file.FileService;
import com.epam.vm.service.settings.ApplicationConstants;
import com.epam.vm.service.settings.impl.PropertiesReaderImpl;

public class FileServiceImpl implements FileService {

	private static final Logger logger = LoggerFactory
			.getLogger(FileServiceImpl.class);

	private static final String folderListSeparator = ";";
	private static final String ILLEGALE_ARGUMENT_TEMPLATE = null;
	private static final String WRONG_FILENAME_LENGTH = null;
	private static final String WRONG_EXTENTION_LENGTH = null;

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
			String videoFileExtention = PropertiesReaderImpl.getInstance()
					.getPropertyValue(ApplicationSetting.VIDEO_FILE_EXTENTION);
			String sybtitleFileExtention = PropertiesReaderImpl.getInstance()
					.getPropertyValue(
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
						logger.info(ILLEGALE_ARGUMENT_TEMPLATE);
						logger.info(
								ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE,
								e);
					} catch (WrongFileNameLength e) {
						logger.info(WRONG_FILENAME_LENGTH);
						logger.info(
								ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE,
								e);
					} catch (WrongExtentionLength e) {
						logger.info(WRONG_EXTENTION_LENGTH);
						logger.info(
								ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE,
								e);
					}
				}
			}
		}
		return filePairs;
	}

	@Override
	public String getFileNameWithoutExtention(String fileName, String extention)
			throws WrongFileNameLength, WrongExtentionLength,
			IllegalArgumentException {
		String returnFileName = null;
		int fileNameLength = fileName.length();
		int extentionLength = extention.length();
		if (fileNameLength <= 0) {
			throw new WrongFileNameLength();
		}
		if (extentionLength <= 0) {
			throw new WrongExtentionLength();
		}
		if ((fileNameLength - extentionLength) <= 1) {
			throw new IllegalArgumentException();
		}
		returnFileName = String.copyValueOf(fileName.toCharArray(), 0,
				fileNameLength - extentionLength);
		return returnFileName;
	}

}
