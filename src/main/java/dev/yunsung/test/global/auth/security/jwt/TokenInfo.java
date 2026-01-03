package dev.yunsung.test.global.auth.security.jwt;

import java.time.Instant;

public record TokenInfo(
	String token,
	Instant expiresAt
) {
	public static TokenInfo of(String token, Instant expiresAt) {
		return new TokenInfo(token, expiresAt);
	}
}
