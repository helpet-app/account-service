package com.helpet.service.account.dto.request;

import com.helpet.validation.annotation.Name;
import com.helpet.validation.annotation.Username;
import lombok.Data;

@Data
public class UpdateAccountRequest {
    @Name(message = "{validations.name.name-is-invalid}")
    private String name;

    @Username(message = "{validations.username.username-is-invalid}")
    private String username;
}
