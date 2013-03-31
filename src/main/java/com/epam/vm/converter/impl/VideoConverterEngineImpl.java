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
import com.epam.vm.exceptions.file.FileException;
import com.epam.vm.exceptions.file.filename.WrongExtentionLengthException;
import com.epam.vm.exceptions.file.filename.WrongFileNameLengthException;
import com.epam.vm.exceptions.file.script.CantDeleteTemporaryScriptException;
import com.epam.vm.exceptions.folder.CantCreateOutputFolderException;
import com.epam.vm.exceptions.folder.NotFolderException;
import com.epam.vm.exceptions.folder.OutputFolderExistsException;
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
import com.epam.vm.service.settings.PropertiesReaderService;
import com.epam.vm.service.settings.impl.PropertiesReaderServiceImpl;

public class VideoConverterEngineImpl implements VideoConverterEngine {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Application.class);
	private static final String MAKING_INPUT_FOLDER_LIST = "Making input folder list";
	private static final String NOT_FOLDER_EXCEPTION = "Input file {} must be a folder";
	private static final String CREATING_OUTPUT_FOLDER = "Creating output folder: {}";
	private static final String CANT_CREATE_FOLDER = "Can't create output folder: {}";
	private static final String MERGING_PAIR = "Marging file: {} with file: {}";
	private static final String NOFILE_TO_MERGE = "Trere are no files to merge in directory: {}";
	private static final String ILLEGALE_ARGUMENTS_EXCEPTION = "Illegale input arguments";
	private static final String WRONG_FILE_NAME_LENGTH = "Wrong file name length: {}";
	private static final String WRONG_EXTENTION_LENGTH = "Wrong extention length: {}";
	private static final String OUTPUT_FOLDER_ALREADY_EXISTS = "Output folder already exists: {}";
	private static final String CANT_DELETE_TEMPORARY_SCRIPT = "Can't delete temporary script file: {}";
	private static final String WRONG_FILE_NAME = null;

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
		LOGGER.info(MAKING_INPUT_FOLDER_LIST);
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
					LOGGER.info(CREATING_OUTPUT_FOLDER, outputFolderName);
					outputFolder = new File(outputFolderName);
					Map<String, String> templatePairs;
					File avsScriptFile = new File(
							properties
									.getPropertyValue(ApplicationSetting.AVS_TEMPORARY_SCRIPT_FILE_NAME));
					List<String> scriptLines;
					StringBuffer outputVideoFileName;
					String commandToRun;
					if (outputFolder.exists()) {
						throw new OutputFolderExistsException(
								outputFolder.getName());
					}
					if (!outputFolder.mkdirs()) {
						throw new CantCreateOutputFolderException(
								outputFolder.getName());
					}
					templatePairs = new HashMap<String, String>();
					for (Entry<File, File> filePair : inputFilePairs.entrySet()) {
						if ((avsScriptFile.exists() && (!avsScriptFile.delete()))) {
							throw new CantDeleteTemporaryScriptException(
									avsScriptFile.getName());
						}
						LOGGER.info(MERGING_PAIR, filePair.getKey(),
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
					LOGGER.info(NOFILE_TO_MERGE);
				}
			} catch (NotFolderException e) {
				isConvertationSuccess = false;
				LOGGER.debug(NOT_FOLDER_EXCEPTION, e.getMessage());
			} catch (IllegalArgumentException e) {
				isConvertationSuccess = false;
				LOGGER.debug(ILLEGALE_ARGUMENTS_EXCEPTION);
			} catch (WrongFileNameLengthException e) {
				isConvertationSuccess = false;
				LOGGER.debug(WRONG_FILE_NAME_LENGTH, e.getMessage());
			} catch (WrongExtentionLengthException e) {
				isConvertationSuccess = false;
				LOGGER.debug(WRONG_EXTENTION_LENGTH, e.getMessage());
			} catch (OutputFolderExistsException e) {
				isConvertationSuccess = false;
				LOGGER.debug(OUTPUT_FOLDER_ALREADY_EXISTS, e.getMessage());
			} catch (CantCreateOutputFolderException e) {
				isConvertationSuccess = false;
				LOGGER.debug(CANT_CREATE_FOLDER, e.getMessage());
			} catch (CantDeleteTemporaryScriptException e) {
				isConvertationSuccess = false;
				LOGGER.debug(CANT_DELETE_TEMPORARY_SCRIPT, e.getMessage());
			} catch (FileException e) {
				isConvertationSuccess = false;
				LOGGER.debug(WRONG_FILE_NAME, e);				
			}
		}
		return isConvertationSuccess;
	}

}
