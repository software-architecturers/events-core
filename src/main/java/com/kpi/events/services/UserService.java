package com.kpi.events.services;

import java.util.*;
import java.util.stream.Collectors;

import com.kpi.events.exceptions.UserNotFoundException;
import com.kpi.events.model.dto.RegisteredUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kpi.events.model.RefreshToken;
import com.kpi.events.model.User;
import com.kpi.events.model.repository.RefreshTokenRepository;
import com.kpi.events.model.repository.UserRepository;
import com.kpi.events.security.filters.JwtTokenUtil;
import com.kpi.events.security.models.RegisterDTO;
import com.kpi.events.security.models.TokenResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Service
public class UserService implements IService<User> {

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private UserRepository repository;
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;


	@Override
	public List<User> findAll(int size, int page) {

		return repository.findAll(new PageRequest(page, size)).getContent();
	}

	@Override
	public User save(User entity) {
		if (find(entity.getLogin()) != null)
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

	public TokenResponse login(User user) {
		User dbUser = repository.findByLogin(user.getLogin());
		if (dbUser == null) {
			throw new RuntimeException("No user with given login exists");
		}

		if (!encoder.matches(user.getPassword(), dbUser.getPassword())) {
			throw new RuntimeException("No user with given login and pass exists");
		}

		TokenResponse token = new TokenResponse(jwtTokenUtil.generateToken(dbUser),
				jwtTokenUtil.generateRefresh(dbUser));

		RefreshToken refresh = new RefreshToken();

		refresh.setToken(token.getRefreshToken());
//        refresh.setUser(dbUser);
		refreshTokenRepository.save(refresh);
		dbUser.getRefreshToken().add(refresh);
		repository.save(dbUser);

		return token;


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

	public User findByRefreshToken(RefreshToken tokenIn) {

		long id = 0;
		String token = tokenIn.getToken();
		//String refreshId = "";
		if (token != null) {
			try {
				id = jwtTokenUtil.getIdFromToken(token);
				//refreshId = jwtTokenUtil.getRefreshIdFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("an error occured during getting username from token" + e);
			} catch (ExpiredJwtException e) {
				System.out.println("the token is expired and not valid anymore" + e);
				System.out.println("refreshID " + e.getClaims().get("refreshId"));
				//refreshId = (String) e.getClaims().get("refreshId");
			} catch (SignatureException e) {
				System.out.println("Authentication Failed. Username or Password not valid.");
			}
		} else {
			System.out.println("couldn't find bearer string, will ignore the header");
		}
		User user = repository.findById(id).orElseThrow(UserNotFoundException::new);
		List<RefreshToken> list = user.getRefreshToken().stream().filter(x -> x.getToken().equals(token)).collect(Collectors
				.toCollection(ArrayList::new));
		if (list.size() == 0) {
			throw new RuntimeException();
		}


		user.getRefreshToken().remove(list.get(0));
		refreshTokenRepository.deleteById(list.get(0).getId());
		repository.save(user);
		return user;
	}

	public TokenResponse refresh(RefreshToken tokenIn) {


		String token = tokenIn.getToken();
		User user = null;
		try {
			user = findByRefreshToken(tokenIn);
		}catch (Throwable throwable) {
			System.out.println("error" + throwable.getMessage());
			throw new UserNotFoundException("Invalid token");
		}
		if (!jwtTokenUtil.validateToken(token, user))
			throw new RuntimeException();
		if (user == null) {
			throw new RuntimeException();
		}
		TokenResponse tokenResponse = new TokenResponse(jwtTokenUtil.generateToken(user),
				jwtTokenUtil.generateRefresh(user));

		RefreshToken refresh = new RefreshToken();
		refresh.setToken(tokenResponse.getRefreshToken());
//        refresh.setUser(user);
		refreshTokenRepository.save(refresh);
		user.getRefreshToken().add(refresh);
		repository.save(user);

		return tokenResponse;
	}

	private String encode(String s) {
		return encoder.encode(s);
	}

	public List<RegisteredUserDto> getUsers() {
		return repository.findAll()
				.stream()
				.map(this::convertToRegisteredDto)
				.collect(Collectors.toList());

	}

	private RegisteredUserDto convertToRegisteredDto(User user) {
		return RegisteredUserDto.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.secondName(user.getSecondName())
				.email(user.getEmail())
				.login(user.getLogin())
				.password(user.getPassword())
				.build();
	}
}
