package com.epam.vm.exceptions.file.filename;

import com.epam.vm.exceptions.file.FileException;

public class WrongExtentionLengthException extends FileException {

	private static final long serialVersionUID = 1L;

	public WrongExtentionLengthException(String extention) {
		super(extention);
	}

}
