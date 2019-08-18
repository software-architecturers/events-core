package com.kpi.events.model;

import com.kpi.events.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.*;
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

    @ManyToMany
    private Set<Event> likedEvents;

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
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(refreshId, user.refreshId) &&
                Objects.equals(userIdGoogle, user.userIdGoogle) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(refreshToken, user.refreshToken) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(secondName, user.secondName) &&
                Objects.equals(priveleges, user.priveleges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, refreshId, userIdGoogle, login, password, email, refreshToken, firstName, secondName, priveleges);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", refreshId='" + refreshId + '\'' +
                ", userIdGoogle='" + userIdGoogle + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", refreshToken=" + refreshToken +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", priveleges=" + priveleges +
                '}';
    }
}