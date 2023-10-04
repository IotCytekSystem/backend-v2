package com.cytek2.cytek.audit.repository;

import com.cytek2.cytek.audit.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByOtp(String otp);
}
