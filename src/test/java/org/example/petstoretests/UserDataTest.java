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
        Random random = new Random();
        ArrayList<String> listOfRandomId = new ArrayList<>();

        ArrayList<User> usersListToAdd = new ArrayList<>();

        for (int j = 0; j < 5; j++) {
            int randomId = random.nextInt();
            usersListToAdd.add(new User(randomId,
                    "name" + randomId,
                    "name" + randomId,
                    "name" + randomId,
                    "name" + randomId + "@gmail.com",
                    "123",
                    j + 1));
            listOfRandomId.add("name" + randomId);
        }

        REQUEST_SERVICE
                .postRequest("user/createWithList", usersListToAdd);

        //Check if all users are added
        listOfRandomId.forEach(item ->
                Assert.assertEquals(
                        REQUEST_SERVICE
                                .getRequest("user/" + item)
                                .getStatusCode(),
                        HttpStatus.SC_OK,
                        "Some of added users are not found"));
    }
}
