package com.example.bebenshop.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.entities.DeviceEntity;
import com.example.bebenshop.repository.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
@Transactional
public class FilterConfig extends OncePerRequestFilter {

    private final DeviceRepository mDeviceRepository;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("basic")) {
            filterChain.doFilter(request, response);
        } else {
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(UNAUTHORIZED.value());
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String type = decodedJWT.getClaim("type").asString();
                    if (type.equals("access")) {
                        DeviceEntity deviceEntity = mDeviceRepository.findByUserAgentAndAccessToken(
                                request.getHeader(USER_AGENT)
                                , token);
                        if (Objects.nonNull(deviceEntity)) {
                            String username = decodedJWT.getSubject();
                            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                            stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(username, null, authorities);
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            filterChain.doFilter(request, response);
                        } else {
                            new ObjectMapper().writeValue(
                                    response.getOutputStream()
                                    , BaseResponseDto.error("Unauthorized.", 401));
                        }
                    } else {
                        new ObjectMapper().writeValue(
                                response.getOutputStream()
                                , BaseResponseDto.error("Unauthorized.", 401));
                    }
                } catch (Exception exception) {
                    new ObjectMapper().writeValue(
                            response.getOutputStream()
                            , BaseResponseDto.error(exception.getMessage(), 401));
                }
            } else {
                new ObjectMapper().writeValue(
                        response.getOutputStream()
                        , BaseResponseDto.error("Unauthorized.", 401));
            }
        }
    }
}