package com.metene.auth;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class InMemoryTokenBlacklist implements TokenBlackList {
    private final Set<String> blackList = new HashSet<>();

    @Override
    public void addToBlackList(String token) {
        blackList.add(token);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blackList.contains(token);
    }
}
