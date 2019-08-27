package com.kpi.events.model.dto;

import com.kpi.events.model.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisteredUserDtoWithToken {
    RegisteredUserDto userDto;
    Set<RefreshToken> userToken;
}
