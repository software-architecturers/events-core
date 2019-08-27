package com.kpi.events.security.filters;

import com.kpi.events.exceptions.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kpi.events.model.User;
import com.kpi.events.model.repository.UserRepository;
import com.kpi.events.security.models.TokenAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String USERNAME_FROM_TOKEN = "an error occured during getting username from token";
    public static final String EXPIRED_TOKEN = "the token is expired and not valid anymore";
    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenUtil.resolveToken(req);
        boolean isCont = true;
        String username = null;
        long id = 0;
        if (token != null) {
            try {
                id = jwtTokenUtil.getIdFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.error(USERNAME_FROM_TOKEN, e);
            } catch (ExpiredJwtException e) {
                logger.warn(EXPIRED_TOKEN, e);
                res.setStatus(401);
                res.getWriter().write("{\"status\": \"expired\"}");
                isCont = false;
            } catch (SignatureException e) {
                logger.error("Authentication Failed. Username or Password not valid.");
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }
        if (id != 0 && SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = repository.findById(id)
                    .orElseThrow(UserNotFoundException::new);

            if (jwtTokenUtil.validateToken(token, user)) {
                TokenAuthentication authentication = new TokenAuthentication(token, user.getAuthorities(), true, user);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        if (isCont) {
            chain.doFilter(req, res);
        }
    }
}
