package com.cytek2.cytek.audit.configurations;

import org.springframework.stereotype.Component;
import java.security.SecureRandom;

@Component
public class TokenGenerator {

    private static final int ACCESS_TOKEN_LENGTH = 32; // Set the desired length of the access token
    private static final int REFRESH_TOKEN_LENGTH = 64; // Set the desired length of the refresh token

    private static final String TOKEN_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateAccessToken() {
        return generateRandomToken(ACCESS_TOKEN_LENGTH);
    }

    public String generateRefreshToken() {
        return generateRandomToken(REFRESH_TOKEN_LENGTH);
    }

    private String generateRandomToken(int length) {
        StringBuilder token = new StringBuilder(length);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(TOKEN_CHARACTERS.length());
            char randomChar = TOKEN_CHARACTERS.charAt(randomIndex);
            token.append(randomChar);
        }

        return token.toString();
    }
}
