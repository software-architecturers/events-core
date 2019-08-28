package com.kpi.events.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredUserDto {
    private long id;

    private String login;

    private String email;

    private String firstName;

    private String secondName;

    private String password;

}
