package com.yamamz;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by admin on 2/18/2018.
 */
@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name="id") private int id;
    @Column(name="last_name") private String lastName;
    @Column(name="first_name") private String firstName;
    @Column(name="email") private String email;
    @Column(name="is_admin") private boolean admin;
    @Column(name="password") private String password;
    @Column(name="user_name") private String username;

    public User() {

    }

    public User(String lastName, String firstName, String email, boolean admin, String password,String username) {
        super();
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.admin = admin;
        this.password = password;
        this.username=username;
    }

    public User(int id, String lastName, String firstName, String email, boolean admin) {
        super();
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

