package org.example.services;

public class UriService {
    private static final String BASE_URI = "https://petstore.swagger.io/v2/";

    public static String prepareUri (String uri) {
        return BASE_URI + uri;
    }
}
