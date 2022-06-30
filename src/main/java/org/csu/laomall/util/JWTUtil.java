package org.csu.laomall.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JWTUtil {
    private static final String SECRET = "LaoMall";
    private static final String ISS = "LaoMall";
    private static final int EXPIRE_TIME = 24 * 60 * 60 * 1000;

    public static String createToken(String username) {
        return createToken(username, EXPIRE_TIME);
    }

    public static String createToken(String username, long expireTime) {
        if (username == null || username.length() == 0) {
            return null;
        }

        Map<String, Object> mapHeader = new HashMap<>(2);
        mapHeader.put("typ", "JWT");
        mapHeader.put("alg", "HS256");

        return JWT.create().withHeader(mapHeader)
                .withClaim("username", username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(SECRET));
    }

    private static Map<String, Claim> verifyToken(String token) throws TokenExpiredException {
        DecodedJWT jwt = null;
        jwt = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);

        return jwt == null ? null : jwt.getClaims();
    }

    public static String getUsername(String token) {
        Claim claim = Objects.requireNonNull(verifyToken(token)).get("username");
        if (claim == null || claim.asString().length() == 0) {
            throw new RuntimeException("token is invalid");
        }
        return claim.asString();
    }
}
