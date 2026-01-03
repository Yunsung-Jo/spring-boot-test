package dev.yunsung.test.global.auth.error.exception;

import dev.yunsung.test.global.auth.error.AuthError;
import io.jsonwebtoken.JwtException;

public class InvalidRefreshTokenException extends JwtException {

	public InvalidRefreshTokenException() {
		super(AuthError.INVALID_REFRESH_TOKEN.getMessage());
	}
}
