package com.helpet.service.account.web.mapper;

import com.helpet.service.account.store.model.Session;
import com.helpet.service.account.web.dto.response.SessionResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessionMapper extends ClassMapper<Session, SessionResponse> {
}
