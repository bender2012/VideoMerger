package com.epam.vm.exceptions.file.script;

import com.epam.vm.exceptions.file.FileException;

public class CantDeleteTemporaryScriptException extends FileException {

	private static final long serialVersionUID = 1L;

	public CantDeleteTemporaryScriptException(String fileName) {
		super(fileName);
	}

}
