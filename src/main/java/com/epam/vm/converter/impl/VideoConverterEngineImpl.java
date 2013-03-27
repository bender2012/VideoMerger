package com.epam.vm.converter.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.vm.app.Application;
import com.epam.vm.converter.VideoConverterEngine;
import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.exceptions.CantCreateOutputFolderException;
import com.epam.vm.exceptions.CantDeleteTemporaryScriptException;
import com.epam.vm.exceptions.NotFolderException;
import com.epam.vm.exceptions.OutputFolderExistsException;
import com.epam.vm.exceptions.WrongExtentionLengthException;
import com.epam.vm.exceptions.WrongFileNameLengthException;
import com.epam.vm.service.execution.ApplicationRunnerService;
import com.epam.vm.service.execution.CommandFormerService;
import com.epam.vm.service.execution.impl.ApplicationRunnerServiceImpl;
import com.epam.vm.service.execution.impl.CommandFormerServiceImpl;
import com.epam.vm.service.file.FileService;
import com.epam.vm.service.file.avs.AvsScriptService;
import com.epam.vm.service.file.avs.impl.AvsScriptServiceImpl;
import com.epam.vm.service.file.impl.FileServiceImpl;
import com.epam.vm.service.file.template.TemplateService;
import com.epam.vm.service.file.template.impl.TemplateServiceImpl;
import com.epam.vm.service.settings.ApplicationConstants;
import com.epam.vm.service.settings.PropertiesReaderService;
import com.epam.vm.service.settings.impl.PropertiesReaderServiceImpl;

public class VideoConverterEngineImpl implements VideoConverterEngine {

	private final static Logger logger = LoggerFactory
			.getLogger(Application.class);
	private String errorMessage;
	private static final String MAKING_INPUT_FOLDER_LIST = "Making input folder list";
	private static final String NOT_FOLDER_EXCEPTION = "Input file must be a folder";
	private static final String CREATING_OUTPUT_FOLDER = "Creating output folder: {}";
	private static final String CANT_CREATE_FOLDER = "Can't create output folder: {}";
	private static final String MERGING_PAIR = "Marging file: {} with file: {}";

	private static FileService fileService;
	private static PropertiesReaderService properties;
	private static TemplateService templateService;
	private static AvsScriptService avsScriptService;
	private static CommandFormerService commandFormerService;
	private static ApplicationRunnerService applicationRunner;

	public VideoConverterEngineImpl() {
		super();
		fileService = new FileServiceImpl();
		properties = PropertiesReaderServiceImpl.getInstance();
		templateService = new TemplateServiceImpl();
		avsScriptService = new AvsScriptServiceImpl();
		commandFormerService = new CommandFormerServiceImpl();
		applicationRunner = new ApplicationRunnerServiceImpl();
	}

	@Override
	public boolean processVideo() {
		boolean isConvertationSuccess = true;
		// Make input file list
		logger.info(MAKING_INPUT_FOLDER_LIST);
		List<File> inputFoldersList = fileService.getInputFolderList(properties
				.getPropertyValue(ApplicationSetting.INPUT_FOLDERS));
		Map<File, File> inputFilePairs = null;
		for (File inputFolder : inputFoldersList) {
			try {
				inputFilePairs = fileService
						.getVideoWithSubtitlesPairs(inputFolder);
				if (inputFilePairs.size() > 0) {
					StringBuffer outputFolderNameToCreateSB;
					String outputFolderName;
					File outputFolder;
					outputFolderNameToCreateSB = new StringBuffer();
					outputFolderNameToCreateSB
							.append(properties
									.getPropertyValue(ApplicationSetting.OUTPUT_FOLDER));
					outputFolderNameToCreateSB.append(File.separator);
					outputFolderNameToCreateSB.append(inputFolder.getName());
					outputFolderName = outputFolderNameToCreateSB.toString();
					// Create output folder
					logger.info(CREATING_OUTPUT_FOLDER, outputFolderName);
					outputFolder = new File(outputFolderName);
					Map<String, String> templatePairs;
					File avsScriptFile = new File(
							properties
									.getPropertyValue(ApplicationSetting.AVS_TEMPORARY_SCRIPT_FILE_NAME));
					List<String> scriptLines;
					StringBuffer outputVideoFileName;
					String commandToRun;
					if (outputFolder.exists()) {// Throw exception
						throw new OutputFolderExistsException(
								outputFolder.getName());
					}
					if (!outputFolder.mkdirs()) {// Throw exception
						throw new CantCreateOutputFolderException(
								outputFolder.getName());
					}
					templatePairs = new HashMap<String, String>();
					for (Entry<File, File> filePair : inputFilePairs.entrySet()) {
						if (avsScriptFile.exists()) {// Throw exception
							if (!avsScriptFile.delete()) {
								throw new CantDeleteTemporaryScriptException(
										avsScriptFile.getName());
							}
						}
						logger.info(MERGING_PAIR, filePair.getKey(),
								filePair.getValue());
						// Form avs-script for each file
						templatePairs.putAll(templateService
								.getVSFilterPathTemplatePair());
						templatePairs
								.putAll(templateService
										.getVideoAndSybtitleFilesTemplatepairs(
												filePair.getKey(),
												filePair.getValue()));
						scriptLines = avsScriptService
								.getScriptCommandsFromTemplate(templatePairs);
						avsScriptFile = fileService.formAvsScriptFile(
								avsScriptFile.getAbsolutePath(), scriptLines);
						// Form command for each file
						outputVideoFileName = new StringBuffer();
						outputVideoFileName.append(outputFolderName);
						outputVideoFileName.append(File.separator);
						outputVideoFileName
								.append(fileService.getFileNameWithoutExtention(
										filePair.getKey().getName(),
										properties
												.getPropertyValue(ApplicationSetting.INPUT_VIDEO_FILE_EXTENTION)));
						outputVideoFileName
								.append(properties
										.getPropertyValue(ApplicationSetting.OUTPUT_VIDEO_FILE_EXTENTION));
						commandToRun = commandFormerService
								.getCommandFromApplicationSettings(
										avsScriptFile.getAbsolutePath(),
										outputVideoFileName.toString());

						// Run each command
						applicationRunner.runApplication(commandToRun);
					}
				} else {
					//logger.info(NOFILE_TO_MERGE);
					// TOTO: log there are no file to convert in current folder
					// inputFolder
				}
			} catch (NotFolderException e) {
				logger.info(NOT_FOLDER_EXCEPTION);
				logger.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WrongFileNameLengthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WrongExtentionLengthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OutputFolderExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CantCreateOutputFolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CantDeleteTemporaryScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isConvertationSuccess;
	}

	private void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}

}
