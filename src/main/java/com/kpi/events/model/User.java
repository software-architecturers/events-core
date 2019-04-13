package com.kpi.events.model;

import com.kpi.events.enums.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Table(name = "user")
@Entity(name = "user")
public class User {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private UserRole role;

    @NotBlank(message = "Blank login")
    @Size(min = 2, max = 45, message = "Wrong login size")
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "Blank first name")
    @Size(min = 2, max = 45, message = "Wrong first name size")
    @Pattern(regexp = "[A-Z]", message = "Wrong syntax in first name")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Blank second name")
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
}