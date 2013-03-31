package com.epam.vm.exceptions.folder;

public class OutputFolderExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public  OutputFolderExistsException(String folderName) {
		super(folderName);		
	}

}
