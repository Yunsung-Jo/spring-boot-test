package dev.yunsung.test.global.auth.error.exception;

import dev.yunsung.test.global.auth.error.AuthError;
import io.jsonwebtoken.JwtException;

public class ExpiredRefreshTokenException extends JwtException {

	public ExpiredRefreshTokenException() {
		super(AuthError.EXPIRED_REFRESH_TOKEN.getMessage());
	}
}
