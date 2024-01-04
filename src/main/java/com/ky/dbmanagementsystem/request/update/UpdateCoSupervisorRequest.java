package com.ky.dbmanagementsystem.request.update;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateCoSupervisorRequest {
    @NotNull
    private String name;
    @NotNull
    private String lastname;
    @NotNull
    private String mail;
}


