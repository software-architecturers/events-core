package com.kpi.events.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.kpi.events.model.User;
import com.kpi.events.model.UserIn;
import com.kpi.events.model.repository.UserRepository;
import com.kpi.events.security.filters.JwtTokenUtil;

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

    
    public String signUp(UserIn user) {
        User dbUser = userRepository.findByLogin(user.getLogin());
        if (dbUser != null) {
            throw new RuntimeException("User already exist.");
        }
        User userToCreate = new User();
        userToCreate.setLogin(user.getLogin());
        
        userToCreate.setPassword(bcryptEncoder.encode(user.getPassword()));
        
        userRepository.save(userToCreate);
        return jwtTokenUtil.generateToken(userToCreate);
    }
}
