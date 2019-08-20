package com.kpi.events.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserVisitorEventDto {
    private long id;

    private String login;

    private String email;

    private String firstName;

    private String secondName;


}
