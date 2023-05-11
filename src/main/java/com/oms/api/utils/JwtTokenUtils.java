package com.oms.api.utils;

import com.alibaba.fastjson.JSON;
import com.oms.api.config.JwtConfig;
import com.oms.api.entity.LoginUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenUtils implements InitializingBean {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private JwtConfig jwtConfig;
    private static final String AUTHORITIES_KEY = "auth";
    private Key key;

    public JwtTokenUtils(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public void afterPropertiesSet() {

        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getBase64Secret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public String createToken(Map<String, Object> claims) {
        return Jwts.builder().claim(AUTHORITIES_KEY, claims).setId(UUID.randomUUID().toString()).setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + jwtConfig.getTokenValidityInSeconds())).compressWith(CompressionCodecs.DEFLATE).signWith(key, SignatureAlgorithm.HS512).compact();
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        HashMap map = (HashMap) claims.get(AUTHORITIES_KEY);
        String userId = map.get("userId").toString();
        String loginUserJson = redisTemplate.boundValueOps("login_user:" + userId).get();
        LoginUser loginUser = JSON.parseObject(loginUserJson, LoginUser.class);
        return new UsernamePasswordAuthenticationToken(loginUser, token, loginUser.getAuthorities());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return false;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}

