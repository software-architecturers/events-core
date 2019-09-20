package com.kpi.events.model.dtos.user;

import com.kpi.events.model.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmallUserDto {
    private long id;

    private String login;

    private String email;

    private String firstName;

    private String secondName;

    private Image image;

    private Integer subscribers;

    private Integer subscriptions;

    private boolean subscribed;
}
