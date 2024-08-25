package com.metene.cookie;

import java.util.NoSuchElementException;

public class CookieNotFoundException extends NoSuchElementException {
    public CookieNotFoundException(String message) {
        super(message);
    }
}
