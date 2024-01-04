package com.ky.dbmanagementsystem.request.create;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateUniversityRequest {
    @NotNull
    private String name;
}
