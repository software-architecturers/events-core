package com.kpi.events.security.filters;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.events.model.OpenIdConnectUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtTokenFilter extends GenericFilterBean {


    private String clientSecret = "PX6ivEkK1QAiSj1xql-isbRQ";


    private String jwkUrl = "https://www.googleapis.com/oauth2/v2/certs" ;

	
	public String getSubject(String token) {
        return JwtHelper.decode(token).getClaims();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) throws Exception {
        try {
           /* Jws<Claims> claims = Jwts.parser().setSigningKey(clientSecret).parseClaimsJws(token);
            
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
*/
            return true;
        } catch (JwtException | IllegalArgumentException e) {
        	e.printStackTrace();
            throw new Exception();
        }
    }

    public JwtTokenFilter() {
       
    }
    
  /*  public Authentication getAuthentication(String token) {
        //UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
    	OpenIdConnectUserDetails user = new OpenIdConnectUserDetails(getUsername(token), token);
        
    	return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
*/
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
        throws IOException, ServletException {
    	
    	System.out.println("Filter 1");

        String token = resolveToken((HttpServletRequest) req);
        try {
			if (token != null && validateToken(token) ) {
				System.out.println("FILTER 1 Found "+ token);
				//System.out.println("FILTER 1 Found "+ getUsername(token));
				System.out.println("FILTER 1 Found "+ getSubject(token));
				String kid = JwtHelper.headers(token).get("kid");
				
				Jwt jwtToken = JwtHelper.decode(token);
				Map<String, String> authInfo = new ObjectMapper()
			              .readValue(jwtToken.getClaims(), Map.class);
				OpenIdConnectUserDetails user = new OpenIdConnectUserDetails(authInfo);
				SecurityContextHolder.getContext().setAuthentication( new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
		        
			
			}
			else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new IOException();
		
		}
        filterChain.doFilter(req, res);
        
    }
    
    private RsaVerifier verifier(String kid) throws Exception {
        JwkProvider provider = new UrlJwkProvider(new URL(jwkUrl));
        Jwk jwk = provider.get(kid);
        return new RsaVerifier((RSAPublicKey) jwk.getPublicKey());
}

}