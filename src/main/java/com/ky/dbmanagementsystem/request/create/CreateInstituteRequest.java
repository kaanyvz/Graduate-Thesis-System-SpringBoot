package com.ky.dbmanagementsystem.request.create;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateInstituteRequest {
    @NotNull
    private String name;
    @NotNull
    private String universityName;
}
