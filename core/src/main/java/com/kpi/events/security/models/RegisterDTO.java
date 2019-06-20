package com.kpi.events.security.models;

import com.kpi.events.model.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegisterDTO extends User {
	
	private String confirmPassword;

}
