package com.ky.dbmanagementsystem.dto;

import com.ky.dbmanagementsystem.model.Thesis;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeywordDto {
    private String id;
    private String name;
}
