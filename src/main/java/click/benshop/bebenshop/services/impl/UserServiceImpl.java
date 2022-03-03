package click.benshop.bebenshop.services.impl;

import click.benshop.bebenshop.entities.RoleEntity;
import click.benshop.bebenshop.entities.UserEntity;
import click.benshop.bebenshop.enums.RoleEnum;
import click.benshop.bebenshop.repository.RoleRepository;
import click.benshop.bebenshop.repository.UserRepository;
import click.benshop.bebenshop.services.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository mUserRepository;
    private final RoleRepository mRoleRepository;
    private final PasswordEncoder mPasswordEncoder;
    private final HttpServletRequest mHttpServletRequest;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Override
    public void createAdmin(UserEntity userEntity) {
        userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
        List<RoleEntity> roleEntityList = mRoleRepository.findAll();
        userEntity.setRoles(roleEntityList);
        mUserRepository.save(userEntity);
    }

    @Override
    public void createUser(UserEntity userEntity) {
        userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
        List<RoleEntity> roleEntityList = mRoleRepository.findAllById(Collections.singleton(2L));
        userEntity.setRoles(roleEntityList);
        mUserRepository.save(userEntity);
    }

    @Override
    public String getUserName() {
        String authorizationHeader = mHttpServletRequest.getHeader(AUTHORIZATION);
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        return decodedJWT.getSubject();
    }

    @Override
    public Boolean isRoleAdmin() {
        return mUserRepository.findByUsername(getUserName()).getRoles().stream().anyMatch(o ->
                o.getName().equals(RoleEnum.ROLE_ADMIN));
    }

    @Override
    public UserEntity getCurrentUser() {
        return mUserRepository.findByUsername(getUserName());
    }
}
