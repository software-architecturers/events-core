package com.kpi.events.security.models;


public class TokenResponse {
	
	String token;
	
	public TokenResponse(String s){
		token = s;
		
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String s) {
		token = s;
	}

}
