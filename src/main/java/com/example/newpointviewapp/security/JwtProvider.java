package com.example.newpointviewapp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtEncoder jwtEncoder;
    @Value("${jwt.expirationInMillis}")
    private Long jwtExpirationInMillis;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return generateTokenWithUsername(user.getUsername());

    }

    private String generateTokenWithUsername(String username)  {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
                .subject(username)
                .claim("scope", "ROLE_USER")
                .build();
        return  this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
