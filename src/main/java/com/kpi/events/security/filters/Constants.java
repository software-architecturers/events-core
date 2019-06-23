package com.kpi.events.security.filters;

public class Constants {

	public static final long REFRESH_TOKEN_VALIDITY_SECONDS = 30*24*60*60;
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "devglan123r";
    public static final String SIGNING_REFRESH_KEY = "devglan123refresh";
    public static final String TOKEN_PARAM = "auth_token";
    public static final String homeUrl = "http://localhost:8080/home";
}
