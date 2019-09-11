package com.kpi.events.model.dto;

import com.kpi.events.model.Image;
import com.kpi.events.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalCabinetDto {
    private long id;

    private String login;

    private String email;

    private String firstName;

    private String secondName;

    private String password;

    private Image image;

    private List<RegisteredUserDto> subscribers;

    private List<RegisteredUserDto> subscriptions;

}

