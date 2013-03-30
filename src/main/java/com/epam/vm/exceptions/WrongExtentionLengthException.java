package com.epam.vm.exceptions;

public class WrongExtentionLengthException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public WrongExtentionLengthException(String extention){
		super(extention);
	}

}
