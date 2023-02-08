package com.example.authservice;

import com.example.authservice.domain.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAccountRepo extends CrudRepository<UserAccount, String> {

	Optional<UserAccount> findByEmail(String email);
}
