package com.kpi.events.security.filters;


import static java.util.Optional.empty;
import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.events.model.User;
import com.kpi.events.services.UserService;

public class OpenIDConnectAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	
	@Autowired
	UserService userService;
	
	public void setRestTemplate(OAuth2RestOperations r) {
		restTemplate = r; 
	}

    private OAuth2RestOperations restTemplate;
    
    @Value("${google.jwkUrl}")
    private String jwkUrl;

    @Value("${google.oauth2.clientId}")
    private String clientId;

    @Value("${google.issuer}")
    private String issuer;

    
    public OpenIDConnectAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(new NoopAuthenticationManager());
        }

    private RsaVerifier verifier(String kid) throws Exception {
        JwkProvider provider = new UrlJwkProvider(new URL(jwkUrl));
        Jwk jwk = provider.get(kid);
        return new RsaVerifier((RSAPublicKey) jwk.getPublicKey());
}
    
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
    	OAuth2AccessToken accessToken = null;
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String token = resolveToken((HttpServletRequest) request);
    	// предлагаю это делать
    	if(auth == null)
    	{
			try {
				//get ccess token from service
				System.out.println("AUTH " + token);
				
				accessToken = restTemplate.getAccessToken();
	        } catch (OAuth2Exception e) {
	        	e.printStackTrace();
	            throw new BadCredentialsException("Could not obtain access token", e);
	        }
	        try {
	            String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
	            String kid = JwtHelper.headers(idToken).get("kid");
	            Jwt tokenDecoded = JwtHelper.decodeAndVerify(idToken, verifier(kid));
	            Map<String, String> authInfo = new ObjectMapper()
	              .readValue(tokenDecoded.getClaims(), Map.class);
	            verifyClaims(authInfo);
	            User user = new User(authInfo, accessToken);
	            System.out.println(accessToken.getAdditionalInformation());
	            System.out.println(accessToken.getTokenType());
	            System.out.println(accessToken.getExpiresIn());
	            System.out.println(accessToken.getScope());
	            System.out.println(accessToken.getRefreshToken());
	            System.out.println(authInfo);
	            System.out.println(user);
	            userService.save(user);
	            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	        } catch (Exception e) {
	        	e.printStackTrace();
	            throw new BadCredentialsException("Could not obtain user details from token", e);
	        }
    	}
    	else
    		return auth;
    				//new UsernamePasswordAuthenticationToken(auth.getPrincipal(), null, auth.getAuthorities());
 	       
    	
    	
    }
    
    private static class NoopAuthenticationManager implements AuthenticationManager {

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            throw new UnsupportedOperationException("No authentication should be done with this AuthenticationManager");
        }

}
    
    public void verifyClaims(Map claims) {
        int exp = (int) claims.get("exp");
        Date expireDate = new Date(exp * 1000L);
        Date now = new Date();
        if (expireDate.before(now) || !claims.get("iss").equals(issuer) || !claims.get("aud").equals(clientId)) {
            throw new RuntimeException("Invalid claims");
        }
}	
}
