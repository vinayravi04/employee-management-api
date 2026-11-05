
package com.sdet.employeemanagement.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ApiConfig {

    private static final String BASE_URL = "http://localhost:8080";

    public static RequestSpecification getRequestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification getResponseSpec() {

        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }
}
