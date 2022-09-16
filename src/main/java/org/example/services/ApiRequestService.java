package org.example.services;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.example.log.Log;

import java.util.function.Function;

public class ApiRequestService {
    private static final String BASE_URI = "https://petstore.swagger.io/v2/";
    private static ApiRequestService instance;

    private final Function<String, String> prepareUri = uri -> String.format("%s%s", BASE_URI, uri);

    protected RequestSpecification requestSpecification;

    public static ApiRequestService getInstance() {
        if (instance == null) {
            instance = new ApiRequestService();
        }

        return instance;
    }

    protected ApiRequestService() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        this.requestSpecification = RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }

    public Response getSuccessRequest(String uri) {
        Log.info("Sending Get request to the URL " + uri);

        Response response = requestSpecification
                .expect().statusCode(HttpStatus.SC_OK)
                .log().ifError()
                .when().get(prepareUri.apply(uri));

        Log.info("Response status code is " + response.statusCode());
        Log.info("Response body is " + response.asPrettyString());
        return response;
    }

    public Response getRequest(String uri) {
        Log.info("Sending Get request to the URL " + uri);

        Response response = requestSpecification
                .expect()
                .log().ifError()
                .when().get(prepareUri.apply(uri));

        Log.info("Response status code is " + response.statusCode());
        Log.info("Response body is " + response.asPrettyString());
        return response;
    }

    public Response postRequest(String uri, Object body) {
        Log.info("Sending Get request to the URL " + uri);

        Response response = requestSpecification
                .body(body)
                .expect().statusCode(HttpStatus.SC_OK)
                .log().ifError()
                .when().post(prepareUri.apply(uri));

        Log.info("Response status code is " + response.statusCode());
        Log.info("Response body is " + response.asPrettyString());

        return response;
    }
}
