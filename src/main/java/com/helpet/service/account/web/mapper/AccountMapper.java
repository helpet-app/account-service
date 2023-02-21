package com.helpet.service.account.web.mapper;

import com.helpet.service.account.store.model.Account;
import com.helpet.service.account.web.dto.response.AccountResponse;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper extends ClassMapper<Account, AccountResponse> {
}
