package com.metene.cookiebanner;

import java.util.NoSuchElementException;

public class CookieBannerNotFoundException extends NoSuchElementException {
    public CookieBannerNotFoundException(String message) {
        super(message);
    }
}
