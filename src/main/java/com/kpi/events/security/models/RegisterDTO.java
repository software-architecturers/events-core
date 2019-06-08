package com.kpi.events.security.models;

import com.kpi.events.model.User;

import lombok.Data;

@Data
public class RegisterDTO extends User {
	
	private String confirmPassword;

}
