package dev.yunsung.test.global.auth.infrastructure.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.yunsung.test.global.auth.application.AuthService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenCleanupScheduler {

	private final AuthService authService;

	@Scheduled(cron = "0 0 4 * * *")
	public void run() {
		authService.deleteExpiredTokens();
	}
}
