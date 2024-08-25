package com.metene.subscription;

import java.util.NoSuchElementException;

public class SubscriptionNotFoundException extends NoSuchElementException {
    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
