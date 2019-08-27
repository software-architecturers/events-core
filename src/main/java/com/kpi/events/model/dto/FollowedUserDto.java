package com.kpi.events.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowedUserDto {

    private RegisteredUserDto user;

    private List<RegisteredUserDto> subscribers;

    private List<RegisteredUserDto> subscriptions;
}
