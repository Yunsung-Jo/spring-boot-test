package dev.yunsung.test.global.auth.presentation.dto.response;

import dev.yunsung.test.global.auth.security.jwt.TokenInfo;

public record TokenResponse(
	String accessToken,
	long expiresIn
) {
	public static TokenResponse of(TokenInfo accessToken) {
		return new TokenResponse(
			accessToken.token(),
			accessToken.expiresIn()
		);
	}
}
