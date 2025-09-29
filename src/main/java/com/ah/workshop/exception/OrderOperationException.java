package com.ah.workshop.exception;

public class OrderOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OrderOperationException(String message) {
        super(message);
    }

}
