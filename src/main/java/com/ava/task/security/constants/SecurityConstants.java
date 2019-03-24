package com.ava.task.security.constants;

public interface SecurityConstants {

	String SECRET = "SecretKeyToGenJWTs";
    long EXPIRATION_TIME = 864_000_000; // 10 days
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    String ROLE = "ROLE";
    String ADMIN = "ADMIN";
	
}
