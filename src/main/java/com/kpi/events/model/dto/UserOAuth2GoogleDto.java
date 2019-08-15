package com.kpi.events.model.dto;

import lombok.Data;

@Data
public class UserOAuth2GoogleDto {

    private String id;

    private String name;

    private String email;

    private String imageUrl;
}
