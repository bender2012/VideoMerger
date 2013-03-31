package com.epam.vm.exceptions.file.filename;

import com.epam.vm.exceptions.file.FileException;

public class WrongFileNameLengthException extends FileException {

	private static final long serialVersionUID = 1L;

	public WrongFileNameLengthException(String fileName) {
		super(fileName);
	}

}
