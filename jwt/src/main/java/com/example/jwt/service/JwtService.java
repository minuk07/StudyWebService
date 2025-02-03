package com.example.jwt.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtService {

    private static String secretKey = "java23SpringBootJWTTokenIssueMethod";

    public String create(
            Map<String, Object> claims, //클레임
            LocalDateTime expireAt//만료 일자
    ){ //라이브러리를 이용하여 토큰 생성

        var key = Keys.hmacShaKeyFor(secretKey.getBytes()); //custom 키 사용
        var _expireAt = Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant()); //날짜 포맷 맞춰주기

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setExpiration(_expireAt) //이 시간이 되면 만료
                .compact();
    }

    public void validation(String token){ //토큰 검증
        var key = Keys.hmacShaKeyFor(secretKey.getBytes()); //custom 키 사용

        var parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        try{
            var result = parser.parseClaimsJwt(token);

            result.getBody().entrySet().forEach(vlaue -> {
                log.info("key : {}, value : {}", vlaue.getKey(), vlaue.getValue());
            });
        }catch(Exception e){
            if(e instanceof SignatureException){
                throw new RuntimeException("JWT Token Not Valid Exception");

            }
            else if(e instanceof ExpiredJwtException){
                throw new RuntimeException("JWT Token Expired Exception");
            }
            else{
                throw new RuntimeException("JWT Token Validation Exception");
            }
        }
    }
}
