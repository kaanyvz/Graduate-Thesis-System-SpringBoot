package com.ky.dbmanagementsystem.request.create;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateAuthorRequest {
    @NotNull
    private String name;
    @NotNull
    private String lastname;
    @NotNull
    private String mail;
}
