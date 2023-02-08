package com.example.authservice;

public interface AuthService {

	String login(LoginRequest req);

	void signUp(SignUpRequest req);

	void changePassword(ChangePasswordRequest req);

	void changeEmail(ChangeEmailRequest req);

	void validateEmail(String emailValidationLink);
}
