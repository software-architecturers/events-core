package com.kpi.events.model.dtos.development;

import com.kpi.events.model.RefreshToken;
import com.kpi.events.model.dtos.user.SmallUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//Class created for development reasons only
public class RegisteredUserDtoWithToken {
    SmallUserDto userDto;
    Set<RefreshToken> userToken;
}
