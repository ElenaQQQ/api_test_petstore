import io.restassured.http.ContentType;
import org.example.entities.Category;
import org.example.entities.Pet;
import org.example.entities.User;
import org.example.services.UriService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class PetsDataTest {

    @Test
    public void addPetTest() {
        Random random = new Random();

        Pet addingPet = new Pet()
                .setId(random.nextInt())
                .setCategory(new Category())
                .setName("Laika")
                .setPhotoUrls(new ArrayList<>())
                .setTags(new ArrayList<>())
                .setStatus("available");

        Pet addedPet = given()
                .body(addingPet)
                .when()
                .contentType(ContentType.JSON)
                .post(UriService.prepareUri("pet"))
                .then().log().all()
                .extract().as(Pet.class);

        Assert.assertEquals("Laika", addedPet.getName(), "Name of added pet is wrong");
    }

    @Test
    public void petsListByStatusTest() {
        List<Pet> petsListAvailable = given()
                .when()
                .contentType(ContentType.JSON)
                .get(UriService.prepareUri("pet/findByStatus?status=available"))
                .then().log().all()
                .extract().body().jsonPath().getList("", Pet.class);
        petsListAvailable.forEach(x -> Assert.assertTrue(x.getStatus().contains("available"),
                "Request for available pets is failed"));
    }

    @Test
    public void getPetByIdTest() {
        Assert.assertEquals(given()
                .when()
                .contentType(ContentType.JSON)
                .get(UriService.prepareUri("pet/1133113311331133")).statusCode(),
                404,
                "It's strange, but we find pet with this random number");
    }

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
