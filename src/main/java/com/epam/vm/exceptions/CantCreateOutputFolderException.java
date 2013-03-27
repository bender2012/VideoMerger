package com.epam.vm.exceptions;

public class CantCreateOutputFolderException extends Exception {

	private static final long serialVersionUID = 1L;

	public CantCreateOutputFolderException(String folderName) {
		super(folderName);
	}

}
