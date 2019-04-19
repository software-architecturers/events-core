package com.kpi.events.security.filters;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.kpi.events.model.UserOAuth2GoogleDto;
import com.kpi.events.model.User;
import com.kpi.events.model.repository.UserRepository;

import java.util.Map;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("load");
    	OidcUser oidcUser = super.loadUser(userRequest);
        Map<String, Object> attributes = oidcUser.getAttributes();
        UserOAuth2GoogleDto userInfo = new UserOAuth2GoogleDto();
        userInfo.setEmail((String) attributes.get("email"));
        userInfo.setId((String) attributes.get("sub"));
        userInfo.setImageUrl((String) attributes.get("picture"));
        userInfo.setName((String) attributes.get("name"));
        updateUser(userInfo);

        return oidcUser;
    }

    private void updateUser(UserOAuth2GoogleDto userInfo) {
        System.out.println("update");
        User user = userRepository.findByLogin(userInfo.getEmail());
        if(user == null) {
            user = new User();
        }
        user.setLogin(userInfo.getEmail());
        //user.setImageUrl(userInfo.getImageUrl());
        user.setFirstName(userInfo.getName());
        user.setSecondName(userInfo.getName());
        
        userRepository.save(user);
    }
}
