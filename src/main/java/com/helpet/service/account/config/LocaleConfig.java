package com.helpet.service.account.config;

import com.helpet.web.util.Localizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfig {
    @Bean
    public AcceptHeaderLocaleResolver localeResolver() {
        Locale defaultLocale = Locale.ENGLISH;
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        Locale.setDefault(defaultLocale);
        localeResolver.setDefaultLocale(defaultLocale);
        return localeResolver;
    }

    @Bean
    public Localizer localizer(@Autowired MessageSource messageSource) {
        return new Localizer(messageSource);
    }
}
