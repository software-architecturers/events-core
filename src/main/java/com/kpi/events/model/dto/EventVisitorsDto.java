package com.kpi.events.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventVisitorsDto {

    private Set<PersonalCabinetDto> users;

    private long count;
}
