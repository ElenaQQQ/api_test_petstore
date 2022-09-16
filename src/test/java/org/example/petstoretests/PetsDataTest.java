package org.example.petstoretests;

import org.apache.http.HttpStatus;
import org.example.entities.Category;
import org.example.entities.Pet;
import org.example.services.ApiRequestService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PetsDataTest {
    private static final ApiRequestService REQUEST_SERVICE = ApiRequestService.getInstance();

    @Test
    public void addPetTest() {
        Pet petToAdd = createPet("Laika");

        Pet addedPet = REQUEST_SERVICE
                .postRequest("pet", petToAdd)
                .as(Pet.class);

        Assert.assertEquals(petToAdd.getName(), addedPet.getName(), "Name of added pet is wrong");
    }

    @Test
    public void petsListByStatusTest() {
        List<Pet> petsListAvailable = REQUEST_SERVICE
                .getSuccessRequest("pet/findByStatus?status=available")
                .jsonPath().getList("", Pet.class);

        petsListAvailable.forEach(x -> Assert.assertTrue(
                x.getStatus().contains("available"), String.format("Request for available pet '%s' is failed", x.getName())));
    }

    @Test
    public void getNotExistingPetByIdTest() {
        Assert.assertEquals(
                REQUEST_SERVICE
                        .getRequest("pet/-1000")
                        .getStatusCode(),
                HttpStatus.SC_NOT_FOUND,
                "It's strange, but we find pet with this random number");
    }

    private Pet createPet(String petName) {
        Random random = new Random();

        return new Pet()
                .setId(random.nextInt(Integer.MAX_VALUE))
                .setCategory(new Category())
                .setName(petName)
                .setPhotoUrls(new ArrayList<>())
                .setTags(new ArrayList<>())
                .setStatus("available");
    }
}
