package com.ky.dbmanagementsystem.request.create;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateLanguageRequest {
    @NotNull
    private String name;
}
