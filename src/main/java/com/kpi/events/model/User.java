package com.kpi.events.model;

import com.kpi.events.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "users_table")
@Data
@Builder
@AllArgsConstructor
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
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<RefreshToken> refreshToken;

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
    @Builder.Default
    private List<String> priveleges = new ArrayList<>();

    public String getPassword() {
        return password;
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

}