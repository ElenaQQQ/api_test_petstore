package org.example.entities;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true) //Due to this annotation we can create all fields to User like chain (User createUser)
public class User {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int userStatus;

    public User() {
    }

    public User(long id, String username, String firstName, String lastName, String email, String password, int userStatus) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userStatus = userStatus;
    }
}
