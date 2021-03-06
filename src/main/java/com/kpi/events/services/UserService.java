package com.kpi.events.services;

import java.util.*;
import java.util.stream.Collectors;

import com.kpi.events.exceptions.UserNotFoundException;
import com.kpi.events.model.Image;
import com.kpi.events.model.dtos.event.EventDto;
import com.kpi.events.model.dtos.user.FullUserDto;
import com.kpi.events.model.dtos.user.SmallUserDto;
import com.kpi.events.model.dtos.development.RegisteredUserDtoWithToken;
import com.kpi.events.model.mapper.EventMapper;
import com.kpi.events.model.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IService<User> {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EventMapper eventMapper;

    @Override
    public List<User> findAll(int size, int page) {

        return repository.findAll(new PageRequest(page, size)).getContent();
    }

    @Override
    public User save(User entity) {
        if (repository.findByLogin(entity.getLogin()).isPresent()) {
            throw new RuntimeException();
        }
        entity.setRefreshId(UUID.randomUUID().toString());
        return repository.save(entity);
    }

    public FullUserDto getUser(long id) {
        return userMapper.convertToFullUserDto(find(id));
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

    @Transactional
    public TokenResponse login(User user) {
        User persistedUser = repository
                .findByLogin(user.getLogin())
                .orElseThrow(() -> new RuntimeException("No user with given login exists"));


        if (!encoder.matches(user.getPassword(), persistedUser.getPassword())) {
            throw new RuntimeException("No user with given login and pass exists");
        }

        TokenResponse token = new TokenResponse(jwtTokenUtil.generateToken(persistedUser),
                jwtTokenUtil.generateRefresh(persistedUser));

        RefreshToken refresh = new RefreshToken();

        refresh.setToken(token.getRefreshToken());
        refreshTokenRepository.save(refresh);
        persistedUser.getRefreshToken().add(refresh);
        repository.save(persistedUser);

        return token;


    }

    @Transactional
    public void register(RegisterDTO user) {
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
        Image image = imageService.find(user.getImage().getId());
        User userEntity = User.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .login(user.getLogin())
                .password(encode(user.getPassword()))
                .priveleges(Arrays.asList("READ_EVENTS", "WRITE_EVENTS"))
                .image(image)
                .build();

        save(userEntity);
    }

    @Transactional
    public User findByRefreshToken(RefreshToken tokenIn) {

        long id = 0;
        String token = tokenIn.getToken();
        //String refreshId = "";
        if (token != null) {
            try {
                id = jwtTokenUtil.getIdFromToken(token);
                //refreshId = jwtTokenUtil.getRefreshIdFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("PIZDEC");
                System.out.println("an error occured during getting username from token" + e);
            } catch (ExpiredJwtException e) {
                System.out.println("PIZDEC");
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

    @Transactional
    public TokenResponse refresh(RefreshToken tokenIn) {


        String token = tokenIn.getToken();
        User user = null;
        try {
            user = findByRefreshToken(tokenIn);
        } catch (Throwable throwable) {
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

    public List<RegisteredUserDtoWithToken> getUsers(Pageable pageable) {
        return repository.findAll(pageable)
                .stream()
                .map(userMapper::convertToRegisteredUserDtoWithToken)
                .collect(Collectors.toList());
    }

    public List<SmallUserDto> getSubscriptions(long id) {
        return find(id).getSubscriptions()
                .stream()
                .map(userMapper::convertToSmallUserDto)
                .collect(Collectors.toList());
    }

    public List<SmallUserDto> getSubscribers(long id) {
        return find(id).getSubscribers()
                .stream()
                .map(userMapper::convertToSmallUserDto)
                .collect(Collectors.toList());
    }


    /**
     * @param userId id of user which you want to follow
     *               persisted user - user which pushed button subscribe
     *               userToFollow - user on which we do subscribe
     * @return updated followed user
     */
    @Transactional
    public FullUserDto subscribeOnUser(long userId) {
        User userToFollow = repository.findById(userId).orElseThrow(UserNotFoundException::new);
        User userRequester = getRequester();
        User persistedUser = repository.findByLogin(userRequester.getLogin()).orElseThrow(UserNotFoundException::new);
        userToFollow.getSubscribers().add(persistedUser);
        persistedUser.getSubscriptions().add(userToFollow);
        return userMapper.convertToFullUserDto(userToFollow);
    }


    public FullUserDto getUserInfo() {
        User userRequester = getRequester();
        User persistedUser = repository.findByLogin(userRequester.getLogin()).orElseThrow(UserNotFoundException::new);
        return userMapper.convertToFullUserDto(persistedUser);
    }

    public List<FullUserDto> find(String search, Pageable pageable) {
        return repository.searchUsers(search.toUpperCase(), pageable)
                .stream()
                .map(user -> userMapper.convertToFullUserDto(user))
                .collect(Collectors.toList());
    }

    public User getRequester() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<EventDto> getEventsWillVisit(long id) {
        User user = repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return user.getVisitedEvents()
                .stream()
                .map(event -> eventMapper.convertToEventDto(event, user))
                .sorted(Comparator.comparing(EventDto::getStartDate))
                .collect(Collectors.toList());
    }

    public List<EventDto> getCreatedEvents(long id) {
        User user = repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return user.getCreatedEvents()
                .stream()
                .map(event -> eventMapper.convertToEventDto(event, user))
                .sorted(Comparator.comparing(EventDto::getStartDate))
                .collect(Collectors.toList());
    }
}
