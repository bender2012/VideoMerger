package com.epam.vm.app;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.vm.enums.ApplicationSetting;
import com.epam.vm.exceptions.NotFolderException;
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

public class Application {

	private final static Logger logger = LoggerFactory
			.getLogger(Application.class);
	private static final String MAKING_INPUT_FOLDER_LIST = "Making input folder list";
	private static final String NOT_FOLDER_EXCEPTION = "Input file must be a folder";
	private static final String CREATING_OUTPUT_FOLDER = "Creating output folder: {}";
	private static final String CANT_CREATE_FOLDER = "Can't create output folder: {}";

	private static FileService fileService;
	private static PropertiesReaderService properties;
	private static TemplateService templateService;
	private static AvsScriptService avsScriptService;
	private static CommandFormerService commandFormerService;
	private static ApplicationRunnerService applicationRunner;

	public Application() {
		fileService = new FileServiceImpl();
		properties = PropertiesReaderServiceImpl.getInstance();
		templateService = new TemplateServiceImpl();
		avsScriptService = new AvsScriptServiceImpl();
		commandFormerService = new CommandFormerServiceImpl();
		applicationRunner = new ApplicationRunnerServiceImpl();
	}

	public static void main(String[] args) {
		Application application = new Application();
		// Make input file list
		logger.info(MAKING_INPUT_FOLDER_LIST);
		List<File> inputFoldersList = fileService.getInputFolderList(properties
				.getPropertyValue(ApplicationSetting.INPUT_FOLDERS));
		Map<File, File> inputFilePairs = null;
		for (File inputFolder : inputFoldersList) {
			try {
				inputFilePairs = fileService.getVideoWithSubtitlesPairs(inputFolder);				
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
					File avsScriptFile = new File(properties.getPropertyValue(ApplicationSetting.AVS_TEMPORARY_SCRIPT_FILE_NAME));
					List<String> scriptLines;
					StringBuffer outputVideoFileName;
					String commandToRun;
					if(!outputFolder.exists() || outputFolder.delete()){					
					if(outputFolder.mkdirs()){
						templatePairs = new HashMap<String, String>();
						for (Entry<File, File> filePair : inputFilePairs.entrySet()) {
							if(avsScriptFile.exists()){
								avsScriptFile.delete();
							}
							//logger.debug(CREATING_TEMPORARY_AVS_SCRIPT, avsScriptFile.getAbsolutePath());
							//avsScriptFile.createNewFile();
							// TODO: Form avs-script for each file
							System.out.println("======================");
							templatePairs.putAll(templateService.getVSFilterPathTemplatePair());
							templatePairs.putAll(templateService.getVideoAndSybtitleFilesTemplatepairs(filePair.getKey(), filePair.getValue()));
//							for (Entry<String, String> entry : templatePairs.entrySet()) {
//								System.out.println(entry.getKey() + " : " + entry.getValue());
//							}
							scriptLines = avsScriptService.getScriptCommandsFromTemplate(templatePairs);
//							System.out.println(avsScriptFile.getAbsolutePath());
							avsScriptFile = fileService.formAvsScriptFile(avsScriptFile.getAbsolutePath(), scriptLines);
							
							
							System.out.println("________________________");
//							System.out.println("AVS LINES:");
//							List<String> lin = fileService.getTextFileLines(avsScriptFile.getAbsolutePath());
//							for (String string : lin) {
//								System.out.println(string);
//							}
//							System.out.println("________________________");
							
							
							
							
							// TODO: Form command for each file
							outputVideoFileName = new StringBuffer();
							outputVideoFileName.append(outputFolderName);
							outputVideoFileName.append(File.separator);
							outputVideoFileName.append(fileService.getFileNameWithoutExtention(filePair.getKey().getName(), properties.getPropertyValue(ApplicationSetting.INPUT_VIDEO_FILE_EXTENTION)));
							outputVideoFileName.append(properties.getPropertyValue(ApplicationSetting.OUTPUT_VIDEO_FILE_EXTENTION));
							System.out.println(outputVideoFileName.toString());
							commandToRun = commandFormerService.getCommandFromApplicationSettings(avsScriptFile.getAbsolutePath(), outputVideoFileName.toString());
							System.out.println(commandToRun);
							
							applicationRunner.runApplication(commandToRun);
							// TODO: Run each command
							
							
							
						}
					}
					} else {
						logger.debug(CANT_CREATE_FOLDER, outputFolderName);
					}
					
					
					
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
			}
		}

		CommandFormerService cf = new CommandFormerServiceImpl();
		System.out.println(cf.getCommandFromApplicationSettings(
				"D:\\tmp\\a.avs", "D:\\tmp\\01_05_.wmv"));

		AvsScriptService a = new AvsScriptServiceImpl();

		/*
		 * LoadPlugin("$VS_FILTER_PATH$") AviSource("$INPUT_VIDEO_FILE$")
		 * TextSub("$INPUT_SYBTITLE_FILE$")
		 */
		Map<String, String> templatePairs = new HashMap<String, String>();
		templatePairs.put("VS_FILTER_PATH", "filter-path");
		templatePairs.put("INPUT_VIDEO_FILE", "input-video-file");
		templatePairs.put("INPUT_SYBTITLE_FILE", "input-sybtitle-file");

		a.getScriptCommandsFromTemplate(templatePairs);
		// a.getScriptCommandsFromTemplate();

	}

}
