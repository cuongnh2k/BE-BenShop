package com.example.bebenshop.config;

import com.example.bebenshop.entities.UserEntity;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailServiceConfig implements UserDetailsService {

    private final UserRepository mUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = mUserRepository.findByUsername(username);
        if (Objects.isNull(userEntity)) {
            throw new BadRequestException(username + " not found in database.");
        } else {
            Collection<SimpleGrantedAuthority> authorities = userEntity.getRoles().stream().map(roleEntity ->
                    new SimpleGrantedAuthority(roleEntity.getName().toString())).collect(Collectors.toList());
            return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
        }
    }
}
