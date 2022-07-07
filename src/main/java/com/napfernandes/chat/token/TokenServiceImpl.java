package com.napfernandes.chat.token;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.napfernandes.chat.login.dto.GenerateTokenInput;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationInMilliseconds}")
    private int jwtInMilliseconds;

    @Override
    public String generateToken(GenerateTokenInput input) {
        JwtBuilder jwtBuilder = Jwts.builder();

        if (input.getSubject() != null) {
            jwtBuilder.setSubject(input.getSubject());
        }

        if (input.getClaims() != null &&
                input.getClaims().size() > 0) {
            jwtBuilder.setClaims(input.getClaims());
        }

        return jwtBuilder.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtInMilliseconds))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
