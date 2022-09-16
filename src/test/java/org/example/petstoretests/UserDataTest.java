package org.example.petstoretests;

import io.restassured.http.ContentType;
import org.example.entities.User;
import org.example.services.UriService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class UserDataTest {
    @Test
    public void addUsersAsArrayTest() {
        Random random = new Random();
        int randomId;
        ArrayList<String> listOfRandomId = new ArrayList<>();

        ArrayList<User> usersListToAdd = new ArrayList<>();

        for (int j = 0; j < 5; j++) {
            randomId = random.nextInt();
            usersListToAdd.add(new User(randomId,
                    "name" + randomId,
                    "name" + randomId,
                    "name" + randomId,
                    "name" + randomId + "@gmail.com",
                    "123",
                    j + 1));
            listOfRandomId.add("name" + randomId);
        }

        int statusCode200 = given()
                .body(usersListToAdd)
                .when()
                .contentType(ContentType.JSON)
                .post(UriService.prepareUri("user/createWithList"))
                .getStatusCode();
        Assert.assertEquals(statusCode200,200,"Adding users as a list is failed");

        //Check if all users are added
        listOfRandomId.forEach(item ->
                Assert.assertEquals(
                        given()
                                .when()
                                .contentType(ContentType.JSON)
                                .get(UriService.prepareUri("user/" + item))
                                .getStatusCode(), 200, "Some of added users are not found"));
    }
}
