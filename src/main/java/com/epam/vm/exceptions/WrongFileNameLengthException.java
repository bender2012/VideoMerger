package com.epam.vm.exceptions;

public class WrongFileNameLengthException extends Exception {

	private static final long serialVersionUID = 1L;

	public WrongFileNameLengthException(String fileName) {
		super(fileName);
	}

}
