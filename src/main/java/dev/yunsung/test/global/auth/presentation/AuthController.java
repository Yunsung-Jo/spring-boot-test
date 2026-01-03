package dev.yunsung.test.global.auth.presentation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.yunsung.test.global.auth.application.AuthService;
import dev.yunsung.test.global.auth.application.dto.AuthTokens;
import dev.yunsung.test.global.auth.presentation.dto.response.TokenResponse;
import dev.yunsung.test.global.auth.util.CookieUtils;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final CookieUtils cookieUtils;

	@PostMapping("/reissue")
	public ResponseEntity<TokenResponse> reissue(
		@CookieValue(name = CookieUtils.REFRESH_TOKEN, required = false) String refreshToken
	) {
		AuthTokens authTokens = authService.reissue(refreshToken);
		ResponseCookie cookie = cookieUtils.createRefreshToken(authTokens.refreshToken().token());
		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(TokenResponse.of(authTokens.accessToken()));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(
		@CookieValue(name = CookieUtils.REFRESH_TOKEN, required = false) String refreshToken
	) {
		authService.logout(refreshToken);
		ResponseCookie cookie = cookieUtils.deleteRefreshToken();
		return ResponseEntity.noContent()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.build();
	}
}
