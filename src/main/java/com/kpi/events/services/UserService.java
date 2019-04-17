package com.kpi.events.services;

import static com.kpi.events.utils.Constants.WRONG_LOGIN;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpi.events.model.Event;
import com.kpi.events.model.User;
import com.kpi.events.model.repository.UserRepository;

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

}
