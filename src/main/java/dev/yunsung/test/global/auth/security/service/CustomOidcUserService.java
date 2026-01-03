package dev.yunsung.test.global.auth.security.service;

import java.util.Objects;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import dev.yunsung.test.global.auth.security.principal.CustomOidcUser;
import dev.yunsung.test.user.domain.Provider;
import dev.yunsung.test.user.domain.User;
import dev.yunsung.test.user.infrastructure.UserRepository;

@Service
public class CustomOidcUserService extends OidcUserService {

	private final UserRepository userRepository;

	public CustomOidcUserService(CustomOAuth2UserService oAuth2UserService, UserRepository userRepository) {
		setOauth2UserService(oAuth2UserService);
		this.userRepository = userRepository;
	}

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = super.loadUser(userRequest);
		Provider provider = Provider.from(userRequest.getClientRegistration().getRegistrationId());
		String providerId = oidcUser.getSubject();
		String nickname = Objects.requireNonNullElse(oidcUser.getNickName(), oidcUser.getFullName());

		User user = userRepository.findByProviderAndProviderId(provider, providerId)
			.orElseGet(() -> userRepository.save(User.create(
				provider,
				providerId,
				nickname,
				oidcUser.getPicture(),
				oidcUser.getEmail()
			)));
		return new CustomOidcUser(oidcUser, user);
	}
}
