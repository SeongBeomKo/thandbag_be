package com.example.thandbag.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.thandbag.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static com.example.thandbag.security.jwt.JwtTokenUtils.*;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final RedisRepository redisRepository;

    public String decodeUsername(String token) {
        DecodedJWT decodedJWT = isValidToken(token)
                .orElseThrow(() -> new IllegalArgumentException(
                        "토큰 정보가 존재하지 않습니다."));

        Date expiredDate = decodedJWT
                .getClaim(CLAIM_EXPIRED_DATE)
                .asDate();

        String username = decodedJWT.getClaim(CLAIM_USER_NAME).asString();

        Date now = new Date();
//        if (expiredDate.before(now)) {
//            if(redisRepository.checkRefreshToken(refreshToken, username)) {
//
//            } else {
//                throw new IllegalArgumentException("토큰 정보가 존재하지 않습니다.");
//            }
//        }


        return username;
    }

    public Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(jwt_secret);
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .build();

            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }
}