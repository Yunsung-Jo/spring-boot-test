package dev.yunsung.test.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Provider provider;

	@Column(nullable = false)
	private String providerId;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String picture;

	@Column(nullable = false)
	private String email;

	@Builder(access = AccessLevel.PRIVATE)
	private User(Provider provider, String providerId, String nickname, String picture, String email) {
		this.provider = provider;
		this.providerId = providerId;
		this.nickname = nickname;
		this.picture = picture;
		this.email = email;
	}

	public static User create(Provider provider, String providerId, String nickname, String picture, String email) {
		return builder()
			.provider(provider)
			.providerId(providerId)
			.nickname(nickname)
			.picture(picture)
			.email(email)
			.build();
	}
}
