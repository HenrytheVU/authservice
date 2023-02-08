package com.example.authservice;

import com.example.authservice.domain.UserAccount;
import lombok.AllArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	final UserAccountRepo repo;
	final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final String PEPPER = "C3n6fLzEY-GEu_ukhjVT4Ly";

	@Override
	public String login(LoginRequest req) {
		val user = repo.findByEmail(req.email()).orElseThrow(InvalidUserCredentials::new);
		if (!validatePw(req.password(), user)) {
			throw new InvalidUserCredentials();
		}
		repo.save(user.withLastLogin(LocalDateTime.now()));
		return "This is a token";
	}

	@Override
	public void signUp(SignUpRequest req) {
		if (repo.findByEmail(req.email()).isPresent()) {
			throw new ClientError("User already signed up");
		}
		val salt = generateSalt();
		val hashedPW = hashPw(req.password(), salt);
		repo.save(new UserAccount(req.email(), hashedPW, req.name(), salt, LocalDateTime.now()));
	}

	@Override
	public void changePassword(ChangePasswordRequest req) {
		val user = repo.findByEmail(req.email()).orElseThrow(InvalidUserCredentials::new);
		if (!validatePw(req.password(), user)) {
			throw new InvalidUserCredentials();
		}
		val salt = generateSalt();
		val newHashedPw = hashPw(req.newPassword(), salt);
		repo.save(user.withPassword(newHashedPw).withSalt(salt));
	}

	@Override
	public void changeEmail(ChangeEmailRequest req) {
		val user = repo.findByEmail(req.email()).orElseThrow(InvalidUserCredentials::new);
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public void validateEmail(String emailValidationLink) {
		// TODO: use jwt to code expiration time into the validationURL
		throw new RuntimeException("Not implemented yet!");
	}

	private boolean validatePw(String rawPassword, UserAccount user) {
		val pwSaltPepper = rawPassword + user.getSalt() + PEPPER;
		return bCryptPasswordEncoder.matches(pwSaltPepper, user.getPassword());
	}

	private String hashPw(String rawPassword, String salt) {
		val pwSaltPepper = rawPassword + salt + PEPPER;
		return bCryptPasswordEncoder.encode(pwSaltPepper);
	}

	private String generateSalt() {
		return RandomStringUtils.randomAlphanumeric(10, 20);
	}
}
