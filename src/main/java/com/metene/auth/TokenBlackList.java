package com.metene.auth;

/**
 * Service for invalidate/Revoked the JWT
 *
 * @author jmetenen
 */
public interface TokenBlackList {
    void addToBlackList(String token);

    boolean isBlacklisted(String token);
}
