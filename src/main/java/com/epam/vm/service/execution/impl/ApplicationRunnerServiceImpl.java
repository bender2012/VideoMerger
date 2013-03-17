package com.epam.vm.service.execution.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.vm.service.execution.ApplicationRunnerService;
import com.epam.vm.service.settings.ApplicationConstants;

public class ApplicationRunnerServiceImpl implements ApplicationRunnerService {

	private static final Logger debugLogger = LoggerFactory
			.getLogger(ApplicationRunnerServiceImpl.class);

	@Override
	public void runApplication(String commandString) {

		Runtime runtime = Runtime.getRuntime();
		try {
			// Process applicationProcess = runtime.exec(commandString);
			// applicationProcess.exitValue();
			Runtime rt = Runtime.getRuntime();
			// Process pr = rt.exec("cmd /c dir");
			Process pr = rt.exec(commandString);
			//String[] ffmpeg = new String[] {"d:\\Programs\\ffmpeg\\bin\\ffmpeg.exe", " -i ", "\"d:\\GIT_Repositories\\VideoMerger\\target\\avs.avs\"", " -t ", " 10 ", "\"d:\\tmp\\output\\Season_1\\01x07 - The One With the Blackout1.wmv\""};
//			String result = "";
//			for (String string : ffmpeg) {
//				result += string;
//			}
			//System.out.println("RESULT: " + result);
			//ffmpeg.exe -i "d:\GIT_Repositories\VideoMerger\target\avs.avs" -t 10 "d:\tmp\output\\Season_1\01x07 - The One With the Blackout1.wmv"
			//Process pr = rt.exec(ffmpeg);

			BufferedReader input = new BufferedReader(new InputStreamReader(
					pr.getInputStream()));
			
			BufferedReader inputErr = new BufferedReader(new InputStreamReader(
					pr.getErrorStream()));

			String line = null;

			while ((line = inputErr.readLine()) != null) {
				System.out.println(line);
			}

			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
			input.close();
			inputErr.close();

			
			int exitVal = pr.waitFor();
			System.out.println("Exited with error code " + exitVal);
		} catch (IOException e) {
			debugLogger
					.debug(ApplicationConstants.EXCEPTION_LOGGER_TEMPLATE, e);
		} catch (IllegalThreadStateException e) {
			// TODO: handle exception
			System.out
					.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
