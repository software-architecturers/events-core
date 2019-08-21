package com.kpi.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users_table")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private long id;

    String refreshId;

    @NotBlank(message = "Blank login")
    @Size(min = 2, max = 45, message = "Wrong login size")
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "Blank first name")
    @Size(min = 2, max = 45, message = "Wrong first name size")
//    @Pattern(regexp = "[A-ZА-Яа-я]", message = "Wrong syntax in first name")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Blank second name")
    @Size(min = 2, max = 45, message = "Wrong second name size")
//    @Pattern(regexp = "[A-ZА-Яа-я]", message = "Wrong syntax in second name")
    @Column(name = "second_name")
    private String secondName;

    private String userIdGoogle;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RefreshToken> refreshToken;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> priveleges = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private Set<Event> createdEvents;

    @ManyToMany
    private Set<Event> likedEvents;

    @ManyToMany(mappedBy = "visitors")
    private Set<Event> visitedEvents;

    public User(Map<String, String> authInfo) {
        this.userIdGoogle = authInfo.get("sub");
        this.login = authInfo.get("email");
    }



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