package com.kpi.events.model;

import com.kpi.events.enums.UserRole;

import lombok.Data;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


@Entity
@Data
public class User implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private long id;
    
    String refreshId;

    private String userIdGoogle;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private UserRole role;

    //    @NotBlank(message = "Blank login")
//    @Size(min = 2, max = 45, message = "Wrong login size")
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    //    @NotBlank(message = "Blank first name")
//    @Size(min = 2, max = 45, message = "Wrong first name size")
//    @Pattern(regexp = "[A-Z]", message = "Wrong syntax in first name")
    @Column(name = "first_name")
    private String firstName;
    //
//    @NotBlank(message = "Blank second name")
//    @Size(min = 2, max = 45, message = "Wrong second name size")
//    @Pattern(regexp = "[A-Z]", message = "Wrong syntax in second name")
//    @Column(name = "second_name")
    private String secondName;

    public User() {

    }

    public User(Map<String, String> userInfo, OAuth2AccessToken token) {
        this.userIdGoogle = userInfo.get("sub");
        this.login = userInfo.get("email");
        this.password = userIdGoogle;
        //this.token = token;
    }


    public User(Map<String, String> authInfo) {
        this.userIdGoogle = authInfo.get("sub");
        this.login = authInfo.get("email");
    }
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> priveleges = new ArrayList<>();
    
    public List<String> getPriveleges() {
    	return priveleges;
    }
    
    public void setPriveleges(List<String> priveleges) {
    	this.priveleges = priveleges;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setUserId(String userId) {
        this.userIdGoogle = userId;
    }

    public String getUserId() {
        return userIdGoogle;
    }
    


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.priveleges.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (role != user.role) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        return secondName != null ? secondName.equals(user.secondName) : user.secondName == null;

    }

    @Override
    public int hashCode() {
        int result = role != null ? role.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userIdGoogle='" + userIdGoogle + '\'' +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }
   
    
    
}