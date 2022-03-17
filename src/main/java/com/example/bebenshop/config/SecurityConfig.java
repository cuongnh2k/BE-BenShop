package com.example.bebenshop.config;

import com.example.bebenshop.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableJpaAuditing
@EnableScheduling
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter implements AuditorAware<String> {

    private final UserDetailServiceConfig mUserDetailServiceConfig;
    private final FilterConfig mFilterConfig;

    @Value("${base.api}")
    private String BASE_API;

    @Value("${domain}")
    private String DOMAIN;

    @Value("${email.name}")
    private String EMAIL_NAME;

    @Value("${email.password}")
    private String EMAIL_PASSWORD;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (Exception ignored) {
            return Optional.of("anonymousUser");
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mUserDetailServiceConfig).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(EMAIL_NAME);
        mailSender.setPassword(EMAIL_PASSWORD);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of(DOMAIN));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers(BASE_API + "/basic/**").permitAll();
        http.authorizeRequests().antMatchers(BASE_API + "/user/**").hasAnyAuthority(RoleEnum.ROLE_USER.toString());
        http.authorizeRequests().antMatchers(BASE_API + "/admin/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.toString());
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(mFilterConfig, UsernamePasswordAuthenticationFilter.class);
    }
}