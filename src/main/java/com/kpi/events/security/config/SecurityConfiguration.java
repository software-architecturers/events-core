package com.kpi.events.security.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kpi.events.security.filters.CustomAuthenticationSuccessHandler;
import com.kpi.events.security.filters.JwtAuthenticationEntryPoint;
import com.kpi.events.security.filters.JwtAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@PropertySource(value = {"classpath:application.properties"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private OidcUserService oidcUserService;

    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService();
    }

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().anonymous().and().authorizeRequests().anyRequest().permitAll();
        http.cors();
        http.csrf().disable().anonymous().and().authorizeRequests().antMatchers("/auth/*")
//                .anonymous().antMatchers(HttpMethod.GET)
                .anonymous().anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .oauth2Login()
                .loginPage("/auth/custom-login")
                .redirectionEndpoint()
                .baseUri("/google-login")
                .and()
                .userInfoEndpoint()
                .oidcUserService(oidcUserService)
                .and()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(customAuthorizationRequestRepository())
                .and()
                .successHandler(customAuthenticationSuccessHandler)
                .and()
                .apply(clientErrorLogging());

        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> customAuthorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClientErrorLoggingConfigurer clientErrorLogging() {
        return new ClientErrorLoggingConfigurer(Arrays.asList(HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN));
    }
}
class ClientErrorLoggingConfigurer
        extends AbstractHttpConfigurer<ClientErrorLoggingConfigurer, HttpSecurity> {

    private List<HttpStatus> errorCodes;

    // standard constructors

    public ClientErrorLoggingConfigurer(List<HttpStatus> errorCodes) {
        this.errorCodes = errorCodes;
    }

    @Override
    public void init(HttpSecurity http) throws Exception {
        // initialization code
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(
                new ClientErrorLoggingFilter(errorCodes),
                FilterSecurityInterceptor.class);
    }
}
class ClientErrorLoggingFilter extends GenericFilterBean {

    private static final Logger logger = LogManager.getLogger(
            ClientErrorLoggingFilter.class);
    private List<HttpStatus> errorCodes;

    public ClientErrorLoggingFilter(List<HttpStatus> errorCodes) {
        this.errorCodes = errorCodes;
    }

    // standard constructor

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        //...
        try {

            chain.doFilter(request, response);
        } catch (Throwable throwable) {
            System.out.println(throwable);
        }
    }
}