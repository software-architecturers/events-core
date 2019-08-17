package com.kpi.events.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.kpi.events.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kpi.events.model.User;
import com.kpi.events.model.repository.UserRepository;
import com.kpi.events.security.filters.JwtTokenUtil;
import com.kpi.events.security.models.RegisterDTO;
import com.kpi.events.security.models.Token;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Service
public class UserService implements IService<User> {
	
	@Autowired
	private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

	@Override
	public List<User> findAll(int size, int page) {

		return repository.findAll(new PageRequest(page, size)).getContent();
	}

	@Override
	public User save(User entity) {
		if(find(entity.getLogin())!= null)
			throw new RuntimeException("User already exists");
		entity.setRefreshId(UUID.randomUUID().toString());
		return repository.save(entity);
		
	}

	@Override
	public User find(long id) {
		return repository.findById(id)
				.orElseThrow(UserNotFoundException::new);
	}

	@Override
	public User update(long id, User entity) {
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

    
//    public String signUp(User user) {
//        User dbUser = userRepository.findByLogin(user.getLogin());
//        if (dbUser != null) {
//            throw new RuntimeException("User already exist.");
//        }
//        User userToCreate = new User();
//        userToCreate.setLogin(user.getLogin());
//
//        userToCreate.setPassword(encode(user.getPassword()));
//
//        save(userToCreate);
//        return jwtTokenUtil.generateToken(userToCreate);
//    }


    public String login(User user) {
        User dbUser = userRepository.findByLogin(user.getLogin());
        if (dbUser == null) {
            throw new RuntimeException("No user with given login exists");
        }

        if(!encoder.matches(user.getPassword(), dbUser.getPassword())) {
        	throw new RuntimeException("No user with given login and pass exists");
        }

        return jwtTokenUtil.generateToken(dbUser);
    }

    public void register(RegisterDTO user) {
		if (!user.getConfirmPassword().equals(user.getPassword())) {
			throw new RuntimeException("Password is incorrect");
		}

		User userEntity = User.builder()
				.email(user.getEmail())
				.firstName(user.getFirstName())
				.secondName(user.getSecondName())
				.login(user.getLogin())
				.password(encode(user.getPassword()))
				.priveleges(Arrays.asList("READ_EVENTS", "WRITE_EVENTS"))
				.build();

		save(userEntity);
	}

	public String refresh(Token tokenIn) {

		//String username = null;
		String token = tokenIn.getToken();
		String refreshId = "";
        if (token != null) {
            try {
                refreshId = jwtTokenUtil.getRefreshIdFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("an error occured during getting username from token" + e);
            } catch (ExpiredJwtException e) {
            	System.out.println("the token is expired and not valid anymore" + e);
            	System.out.println("refreshID " + e.getClaims().get("refreshId"));
            	refreshId = (String) e.getClaims().get("refreshId");
            } catch(SignatureException e){
            	System.out.println("Authentication Failed. Username or Password not valid.");
            }
        } else {
        	System.out.println("couldn't find bearer string, will ignore the header");
        }
        User user = repository.findByRefreshId(refreshId);
        user.setRefreshId(UUID.randomUUID().toString());
        repository.save(user);
        if(!jwtTokenUtil.validateToken(token, user))
        	return "";
        if(user == null) {
        	return "";
        }
		return jwtTokenUtil.generateToken(user);
	}

	private String encode(String s) {
		return encoder.encode(s);
	}
}
