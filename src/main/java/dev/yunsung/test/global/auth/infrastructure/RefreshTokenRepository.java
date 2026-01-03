package dev.yunsung.test.global.auth.infrastructure;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dev.yunsung.test.global.auth.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
	Optional<RefreshToken> findByToken(String token);
}
