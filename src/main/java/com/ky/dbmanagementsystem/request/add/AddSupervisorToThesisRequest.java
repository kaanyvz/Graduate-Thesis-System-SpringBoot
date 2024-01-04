package com.ky.dbmanagementsystem.request.add;

import lombok.Getter;

@Getter
public class AddSupervisorToThesisRequest {
    private String supervisorName;
    private String supervisorLastname;
    private Integer thesisNo;
}
