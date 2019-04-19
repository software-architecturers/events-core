package com.kpi.events.security.client;


import static java.util.Arrays.asList;
import static org.springframework.security.oauth2.common.AuthenticationScheme.*;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class OAuth2Client {
    @Value("${google.oauth2.clientId}")
    private String clientId;

    @Value("${google.oauth2.clientSecret}")
    private String clientSecret;

    @Value("${google.redirectUri}")
    private static String redirectUri;

    @Bean
    // TODO retrieve from https://accounts.google.com/.well-known/openid-configuration ?
    public OAuth2ProtectedResourceDetails googleOAuth2Details() {
        AuthorizationCodeResourceDetails googleOAuth2Details = new AuthorizationCodeResourceDetails();
        googleOAuth2Details.setAuthenticationScheme(header);
        googleOAuth2Details.setClientAuthenticationScheme(header);
        googleOAuth2Details.setClientId(clientId);
        googleOAuth2Details.setClientSecret(clientSecret);
        googleOAuth2Details.setPreEstablishedRedirectUri(redirectUri);
        googleOAuth2Details.setUserAuthorizationUri("https://accounts.google.com/o/oauth2/auth");
        googleOAuth2Details.setAccessTokenUri("https://www.googleapis.com/oauth2/v3/token");
        googleOAuth2Details.setScope(asList("openid", "email", "profile"));
        return googleOAuth2Details;
    }

    @SuppressWarnings("SpringJavaAutowiringInspection") // Provided by Spring Boot
    @Resource
    private OAuth2ClientContext oAuth2ClientContext;

    @Bean
    @Primary
    public OAuth2RestTemplate googleOAuth2RestTemplate() {
        return new OAuth2RestTemplate(googleOAuth2Details(), oAuth2ClientContext);
    }
}