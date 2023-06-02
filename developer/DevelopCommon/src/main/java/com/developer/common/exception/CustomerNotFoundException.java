package com.developer.common.exception;

public class CustomerNotFoundException extends Exception {

	public CustomerNotFoundException(String name) {
		super(name);
	}
}
