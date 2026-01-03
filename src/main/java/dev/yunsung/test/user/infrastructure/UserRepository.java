package dev.yunsung.test.user.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.yunsung.test.user.domain.Provider;
import dev.yunsung.test.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByProviderAndProviderId(Provider provider, String providerId);
}
