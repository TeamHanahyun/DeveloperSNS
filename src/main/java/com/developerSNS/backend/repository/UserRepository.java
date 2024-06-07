package com.developerSNS.backend.repository;

import com.developerSNS.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String nickname);
    boolean existsByEmail(String email);
}
