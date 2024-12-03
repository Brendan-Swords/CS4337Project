package cs4337.group6.AuthenticationService.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService
{
    private String secretKey = "";

    public JWTService()
    {
        try
        {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGenerator.generateKey();
            this.secretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }

    public String GenerateToken(String username)
    {
        Map<String, Object> claims = new HashMap<String, Object>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (30 * 60 * 1000))) // + 30 mins ahead.
                .and()
                .signWith(GetKey())
                .compact();
    }

    private SecretKey GetKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String ExtractUserName(String token)
    {
        // extract the username from jwt token
        return ExtractClaim(token, Claims::getSubject);
    }

    private <T> T ExtractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = ExtractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims ExtractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(GetKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean ValidateToken(String token, UserDetails userDetails)
    {
        final String userName = ExtractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return ExtractExpiration(token).before(new Date());
    }

    private Date ExtractExpiration(String token) {
        return ExtractClaim(token, Claims::getExpiration);
    }
}
