package com.kpi.events.security.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.kpi.events.security.filters.JwtTokenFilter;
import com.kpi.events.security.filters.OpenIDConnectAuthenticationFilter;

@Configuration
@EnableWebSecurity
@PropertySource(value={"classpath:application.properties"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
    @Autowired
    private OAuth2RestTemplate r;

    
    private final String LOGIN_URL = "/google-login";

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint(LOGIN_URL);
    }

    @Bean
    public OpenIDConnectAuthenticationFilter openIdConnectAuthenticationFilter() {
        OpenIDConnectAuthenticationFilter filter = new OpenIDConnectAuthenticationFilter(LOGIN_URL);
        filter.setRestTemplate(r);
        return filter;
    }

    @Bean
    public OAuth2ClientContextFilter oAuth2ClientContextFilter() {
        return new OAuth2ClientContextFilter();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       
        
        http.csrf().disable()
        .addFilterAfter(oAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
        .addFilterAfter(openIdConnectAuthenticationFilter(), OAuth2ClientContextFilter.class)
        .addFilterBefore(new JwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
        .and().authorizeRequests()
        .antMatchers(GET, "/google-login").permitAll()
        .antMatchers(POST, "/logout").permitAll()
		.anyRequest().authenticated()
		.and().logout().logoutUrl("/logout").logoutSuccessUrl("/suc").permitAll();
    }
    
    
}