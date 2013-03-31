package com.epam.vm.exceptions.folder;

public class NotFolderException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotFolderException(String folderName) {
		super(folderName);
	}

}
