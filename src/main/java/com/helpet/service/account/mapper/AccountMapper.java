package com.helpet.service.account.mapper;

import com.helpet.service.account.dto.response.AccountResponse;
import com.helpet.service.account.store.model.Account;
import com.helpet.web.mapper.ClassMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper extends ClassMapper<Account, AccountResponse> {
}
