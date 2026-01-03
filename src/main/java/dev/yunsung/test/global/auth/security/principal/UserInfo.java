package dev.yunsung.test.global.auth.security.principal;

import io.jsonwebtoken.Claims;

public record UserInfo(Long id) {

	public static UserInfo from(Claims claims) {
		return new UserInfo(
			Long.valueOf(claims.getSubject())
		);
	}

	public static UserInfo from(Object principal) {
		return switch (principal) {
			case CustomOidcUser oidcUser -> new UserInfo(oidcUser.getId());
			default -> (UserInfo)principal;
		};
	}
}
