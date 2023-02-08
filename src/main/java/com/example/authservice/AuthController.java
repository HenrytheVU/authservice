package com.example.authservice;

import com.example.authservice.hateoas.Link;
import com.example.authservice.hateoas.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	final AuthService service;
	final JwtService jwtService;

	@GetMapping
	public Resource<?> home(UriComponentsBuilder uriComponentsBuilder) {
		val p = new Person("henry", "vu");
		val resource = new Resource<>(p);
		val loginLink = new Link("login", "auth/login");
		val signUpLink = new Link("signUp", "auth/login");
		resource.addLink(uriComponentsBuilder, loginLink, signUpLink);
		return resource;
	}

	@PostMapping("/login")
	public String login(@RequestBody @Valid LoginRequest req) {
		return service.login(req);
	}

	@PostMapping("/signUp")
	public String signUp(@RequestBody @Valid SignUpRequest req) {
		service.signUp(req);
		return "User successfully signed up";
	}

	@GetMapping("/token")
	public String token() {
		return jwtService.createToken("Henry", List.of("this", "that"));
	}

	@PostMapping("/verify")
	public String verifyToken(@RequestBody String token) {
		return jwtService.verifyToken(token) ? "valid" : "invalid";
	}

	@PutMapping("/passwords")
	public String changePassword(@RequestBody @Valid ChangePasswordRequest req) {
		service.changePassword(req);
		return "Password successfully changed";
	}

}

record Person(String firstName, String lastName) {

}

record LoginRequest(@Email String email, String password) {
}

record SignUpRequest(@Email String email,
                     @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{10,}$") String password, String name) {
}

record ChangePasswordRequest(@Email String email,
                             @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{10,}$") String password,
                             @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{10,}$") String newPassword) {
}

record ChangeEmailRequest(@Email String email, @Email String newEmail) {
}