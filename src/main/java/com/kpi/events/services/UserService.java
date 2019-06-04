package com.kpi.events.services;

import static com.kpi.events.utils.Constants.WRONG_LOGIN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kpi.events.model.Event;
import com.kpi.events.model.User;
import com.kpi.events.model.UserIn;
import com.kpi.events.model.repository.UserRepository;
import com.kpi.events.security.filters.JwtTokenUtil;
import com.kpi.events.security.models.Token;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Service
public class UserService implements IService<User> {
	
    @Autowired
    private UserRepository repository;

	@Override
	public List<User> findAll(int size, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User save(User entity) {
		if(find(entity.getLogin())!= null)
			throw new RuntimeException("User already exists");
		long x = 1000L;
		long y = 999999999999L;
		Random r = new Random();
		long number = x+((long)(r.nextDouble()*(y-x)));
		entity.setResfreshId(number);
		return repository.save(entity);
		
	}

	@Override
	public User find(long id) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public User update(long id, User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}


	public User find(String name) {
		return repository.findByLogin(name);
		
	}

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    
 
    
    public String signUp(User user) {
        User dbUser = userRepository.findByLogin(user.getLogin());
        if (dbUser != null) {
            throw new RuntimeException("User already exist.");
        }
        User userToCreate = new User();
        userToCreate.setLogin(user.getLogin());
        
        userToCreate.setPassword(bcryptEncoder.encode(user.getPassword()));
        
        save(userToCreate);
        return jwtTokenUtil.generateToken(userToCreate);
    }
    
    
    public String login(User user) {
        User dbUser = userRepository.findByLogin(user.getLogin());
        if (dbUser == null) {
            throw new RuntimeException("No user with given cred exists");
        }
        
        if(!user.getPassword().equals(dbUser.getPassword())) {
        	throw new RuntimeException("No user with given cred exists");
        }
        
        return jwtTokenUtil.generateToken(dbUser);
    }
    
    public void register(User user){
    	
    	user.setPriveleges(new ArrayList<String>(Arrays.asList("READ_EVENTS", "WRITE_EVENTS")));
    	System.out.println(user);
    	save(user);
    	
    	
    }

	public String refresh(Token tokenIn) {
		
		String username = null;
		String token = tokenIn.getToken();
		long refreshId = 0;
        if (token != null) {
            try {
                username = jwtTokenUtil.getUsernameFromToken(token);
                refreshId = jwtTokenUtil.getRefreshIdFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("an error occured during getting username from token" + e);
            } catch (ExpiredJwtException e) {
            	System.out.println("the token is expired and not valid anymore" + e);
            	System.out.println("refreshID " + e.getClaims().get("refreshId"));
            	refreshId = (Long) e.getClaims().get("refreshId");
            } catch(SignatureException e){
            	System.out.println("Authentication Failed. Username or Password not valid.");
            }
        } else {
        	System.out.println("couldn't find bearer string, will ignore the header");
        }
        User user = repository.findByRefreshId(refreshId);
        user.updateRefreshId();
        repository.save(user);
        if(!jwtTokenUtil.validateToken(token, user))
        	return "";
        if(user==null) {
        	return "";
        }
		return jwtTokenUtil.generateToken(user);
	}
}
