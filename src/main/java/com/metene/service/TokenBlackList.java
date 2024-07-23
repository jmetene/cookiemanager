package com.metene.service;

/**
 * Service for invalidate/Revoked the JWT
 *
 * @author jmetenen
 */
public interface TokenBlackList {
    void addToBlackList(String token);

    boolean isBlacklisted(String token);
}
