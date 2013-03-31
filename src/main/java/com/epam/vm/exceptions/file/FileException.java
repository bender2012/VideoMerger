package com.epam.vm.exceptions.file;

public abstract class FileException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public FileException(String fileName){
		super(fileName);
	}

}
