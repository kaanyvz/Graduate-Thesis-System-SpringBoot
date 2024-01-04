package com.ky.dbmanagementsystem.request.update;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateKeywordRequest {
    @NotNull
    private String name;
}
