package dev.yunsung.test.global.auth.security.principal;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import dev.yunsung.test.user.domain.Provider;
import dev.yunsung.test.user.domain.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class CustomOidcUser extends DefaultOidcUser {

	private final Long id;
	private final Provider provider;
	private final String nickname;

	public CustomOidcUser(OidcUser oidcUser, User user) {
		super(oidcUser.getAuthorities(), oidcUser.getIdToken(), oidcUser.getUserInfo());
		this.id = user.getId();
		this.provider = user.getProvider();
		this.nickname = user.getNickname();
	}
}
