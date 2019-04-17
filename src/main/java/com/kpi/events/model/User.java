package com.kpi.events.model;

import com.kpi.events.enums.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Table(name = "user")
@Entity(name = "user")
public class User  implements UserDetails  {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private UserRole role;
    

    private String userIdGoogle;

    @NotBlank(message = "Blank login")
    @Size(min = 2, max = 45, message = "Wrong login size")
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    //@NotBlank(message = "Blank first name")
    @Size(min = 2, max = 45, message = "Wrong first name size")
    @Pattern(regexp = "[A-Z]", message = "Wrong syntax in first name")
    @Column(name = "first_name")
    private String firstName;

   // @NotBlank(message = "Blank second name")
    @Size(min = 2, max = 45, message = "Wrong second name size")
    @Pattern(regexp = "[A-Z]", message = "Wrong syntax in second name")
    @Column(name = "second_name")
    private String secondName;

    /**
     * The users you follow
     * (You are subscriber)
     */
    @OneToMany(cascade = {CascadeType.ALL})
    private List<User> followings;

    /**
     * The users follow you
     * (Your subscribers)
     */
    @OneToMany(cascade = {CascadeType.ALL})
    private List<User> followers;


    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowings() {
        return followings;
    }

    public void setFollowings(List<User> followings) {
        this.followings = followings;
    }

    public int getId() {
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
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", role=" + role +
                ", followers=" + followers +
                ", followings=" + followings +
                '}';
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
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
}