package click.benshop.bebenshop.config;

import click.benshop.bebenshop.dto.produces.TokenProduceDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenConfig {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.access.token.validity}")
    private Long JWT_ACCESS_TOKEN_VALIDITY;

    @Value("${jwt.refresh.token.validity}")
    private Long JWT_REFRESH_TOKEN_VALIDITY;

    public TokenProduceDto generateToken(UserDetails userDetails, HttpServletRequest request) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        return TokenProduceDto.builder()
                .accessToken(JWT.create()
                        .withSubject(userDetails.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .withClaim("type", "access")
                        .sign(algorithm))
                .refreshToken(JWT.create()
                        .withSubject(userDetails.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("type", "refresh")
                        .sign(algorithm))
                .build();
    }
}
