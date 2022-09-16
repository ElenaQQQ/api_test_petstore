package org.example.petstoretests;

import org.apache.http.HttpStatus;
import org.example.entities.User;
import org.example.services.ApiRequestService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Random;

public class UserDataTest {
    private static final ApiRequestService REQUEST_SERVICE = ApiRequestService.getInstance();

    @Test
    public void addUsersAsArrayTest() {
        ArrayList<User> usersListToAdd = createRandomUsers(5);

        REQUEST_SERVICE
                .postRequest("user/createWithList", usersListToAdd);

        //Check if all users are added
        usersListToAdd.forEach(user ->
                Assert.assertEquals(
                        REQUEST_SERVICE
                                .getRequest("user/" + user.getUsername())
                                .getStatusCode(),
                        HttpStatus.SC_OK,
                        String.format("Created user '%s' was not found", user.getUsername())));
    }

    private ArrayList<User> createRandomUsers(int count) {
        ArrayList<User> users = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            users.add(createRandomUser());
        }

        return users;
    }

    private User createRandomUser() {
        Random random = new Random();
        int randomId = random.nextInt(Integer.MAX_VALUE);
        return new User(randomId,
                "name" + randomId,
                "name" + randomId,
                "name" + randomId,
                "name" + randomId + "@gmail.com",
                "123",
                1);
    }
}
