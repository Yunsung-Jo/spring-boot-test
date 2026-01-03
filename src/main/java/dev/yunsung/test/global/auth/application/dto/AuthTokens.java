package dev.yunsung.test.global.auth.application.dto;

import dev.yunsung.test.global.auth.security.jwt.TokenInfo;

public record AuthTokens(
	TokenInfo accessToken,
	TokenInfo refreshToken
) {
	public static AuthTokens of(TokenInfo accessToken, TokenInfo refreshToken) {
		return new AuthTokens(accessToken, refreshToken);
	}
}
