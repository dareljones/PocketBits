package com.pocketbits.demo.service;

public class CoinAlreadyPresentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CoinAlreadyPresentException() {
        super("User already has the specified coin!");
    }
}
