package com.helpet.service.account.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Instant;

@Converter
public class InstantToStringConverter implements AttributeConverter<Instant, String> {
    @Override
    public String convertToDatabaseColumn(Instant instant) {
        return instant.toString();
    }

    @Override
    public Instant convertToEntityAttribute(String s) {
        return Instant.parse(s);
    }
}
