package dev.yunsung.test.global.auth.application;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import dev.yunsung.test.global.auth.application.dto.AuthTokens;
import dev.yunsung.test.global.auth.domain.RefreshToken;
import dev.yunsung.test.global.auth.error.exception.ExpiredRefreshTokenException;
import dev.yunsung.test.global.auth.error.exception.InvalidRefreshTokenException;
import dev.yunsung.test.global.auth.infrastructure.RefreshTokenRepository;
import dev.yunsung.test.global.auth.security.jwt.JwtCode;
import dev.yunsung.test.global.auth.security.jwt.JwtTokenProvider;
import dev.yunsung.test.global.auth.security.jwt.TokenInfo;
import dev.yunsung.test.global.auth.security.principal.UserInfo;
import dev.yunsung.test.global.auth.util.HashUtils;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	public AuthTokens reissue(String refreshToken) {
		JwtCode jwtCode = jwtTokenProvider.validateRefreshToken(refreshToken);
		if (JwtCode.INVALID_TOKEN == jwtCode) {
			throw new InvalidRefreshTokenException();
		}

		String hashedRefreshToken = HashUtils.generateHash(refreshToken);
		RefreshToken savedToken = refreshTokenRepository.findByToken(hashedRefreshToken)
			.orElseThrow(InvalidRefreshTokenException::new);

		if (savedToken.isExpired()) {
			refreshTokenRepository.delete(savedToken);
			throw new ExpiredRefreshTokenException();
		}

		refreshTokenRepository.delete(savedToken);
		Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
		return generateToken(authentication);
	}

	private AuthTokens generateToken(Authentication authentication) {
		UserInfo userInfo = UserInfo.from(authentication.getPrincipal());
		TokenInfo accessToken = jwtTokenProvider.generateAccessToken(userInfo);
		TokenInfo refreshToken = generateRefreshToken(authentication);
		return AuthTokens.of(accessToken, refreshToken);
	}

	public TokenInfo generateRefreshToken(Authentication authentication) {
		UserInfo userInfo = UserInfo.from(authentication.getPrincipal());
		TokenInfo tokenInfo = jwtTokenProvider.generateRefreshToken(userInfo);

		RefreshToken refreshToken = RefreshToken.create(
			userInfo.id(),
			HashUtils.generateHash(tokenInfo.token()),
			tokenInfo.expiresAt()
		);

		refreshTokenRepository.save(refreshToken);
		return tokenInfo;
	}

	public void logout(String refreshToken) {
		if (ObjectUtils.isEmpty(refreshToken)) {
			return;
		}
		String hashedRefreshToken = HashUtils.generateHash(refreshToken);
		refreshTokenRepository.findByToken(hashedRefreshToken)
			.ifPresent(refreshTokenRepository::delete);
	}
}
