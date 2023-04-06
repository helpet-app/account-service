package com.helpet.service.account.mapper;

import com.helpet.service.account.storage.model.Session;
import com.helpet.service.account.dto.response.SessionResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessionMapper extends ClassMapper<Session, SessionResponse> {
}
