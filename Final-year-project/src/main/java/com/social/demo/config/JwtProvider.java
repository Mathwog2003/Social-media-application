package com.social.demo.config;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtProvider {
    private SecretKey Key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
}
