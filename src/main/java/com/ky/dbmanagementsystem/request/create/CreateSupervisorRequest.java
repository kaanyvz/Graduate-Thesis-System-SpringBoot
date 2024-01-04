package com.ky.dbmanagementsystem.request.create;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateSupervisorRequest {
    @NotNull
    private String name;
    @NotNull
    private String lastname;

}

