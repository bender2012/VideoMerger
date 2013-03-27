package com.epam.vm.exceptions;

public class CantDeleteTemporaryScriptException extends Exception {

	private static final long serialVersionUID = 1L;

	public CantDeleteTemporaryScriptException(String fileName) {
		super(fileName);
	}

}
