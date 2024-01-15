package com.woof.api.member.repository;

import com.woof.model.EmailVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {
    Optional<EmailVerify> findByEmail(String email);
}
