package com.ss.hanarowa.global.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenBlacklistService {
    private final Map<String, Long> blacklistedTokens = new ConcurrentHashMap<>();
    
    public void blacklistToken(String token, long expirationTime) {
        blacklistedTokens.put(token, expirationTime);
        log.info("토큰이 블랙리스트에 추가됨: {}", token.substring(0, Math.min(20, token.length())) + "...");
        
        // 만료된 토큰들을 주기적으로 정리
        cleanExpiredTokens();
    }
    
    public boolean isBlacklisted(String token) {
        Long expirationTime = blacklistedTokens.get(token);
        if (expirationTime == null) {
            return false;
        }
        
        // 토큰이 만료되었으면 블랙리스트에서 제거
        if (System.currentTimeMillis() > expirationTime) {
            blacklistedTokens.remove(token);
            return false;
        }
        
        return true;
    }
    
    private void cleanExpiredTokens() {
        long currentTime = System.currentTimeMillis();
        blacklistedTokens.entrySet().removeIf(entry -> currentTime > entry.getValue());
    }
}