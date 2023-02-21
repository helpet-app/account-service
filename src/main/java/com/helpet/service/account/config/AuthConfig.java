package com.helpet.service.account.config;

import com.helpet.service.account.store.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;

@Configuration
public class AuthConfig {
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService,
                                                        PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Qualifier("jwtRefreshTokenAuthenticationProvider")
    @Bean
    JwtAuthenticationProvider jwtRefreshTokenAuthenticationProvider(@Qualifier("jwtRefreshTokenDecoder") JwtDecoder jwtRefreshTokenDecoder) {
        return new JwtAuthenticationProvider(jwtRefreshTokenDecoder);
    }

    @Autowired
    @Bean
    public UserDetailsService userDetailsService(AccountRepository accountRepository) throws UsernameNotFoundException {
        return username -> accountRepository.findAccountByUsername(username)
                                            .orElseThrow(() -> new UsernameNotFoundException("Account with username '" + username + "' does not exist"));
    }
}
