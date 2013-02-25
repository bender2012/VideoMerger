package com.epam.vm.service.execution;

public interface CommandFormer {

	String getCommandFromApplicationSettings(String inputFilePath,
			String outputFilePath);

}
