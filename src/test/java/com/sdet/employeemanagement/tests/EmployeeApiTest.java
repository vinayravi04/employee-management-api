
package com.sdet.employeemanagement.tests;

import com.sdet.employeemanagement.config.ApiConfig;
import com.sdet.employeemanagement.testdata.EmployeeTestData;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class EmployeeApiTest {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeApiTest.class);

    private final RequestSpecification requestSpec =
            ApiConfig.getRequestSpec();

    private final ResponseSpecification responseSpec =
            ApiConfig.getResponseSpec();

    // Test 1: Get all employees
    @Test(priority = 1)
    public void getAllEmployeesTest() {

        given()
                .spec(requestSpec)
                .when()
                .get("/employees")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("[0].id", equalTo(1))
                .body("[0].email", equalTo("john@gmail.com"))
                .body("[0].department", equalTo("IT"))
                .body("size()", greaterThanOrEqualTo(2));
    }

    // Test 2: Get employee by ID
    @Test(priority = 2)
    public void getEmployeeByIdTest() {

        given()
                .spec(requestSpec)
                .pathParam("id", 1)
                .when()
                .get("/employees/{id}")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("id", equalTo(1))
                .body("email", equalTo("john@gmail.com"));
    }

    // Test 3: Create employee
    @Test(priority = 3)
    public void createEmployeeTest() {

        given()
                .spec(requestSpec)
                .body(EmployeeTestData.validEmployee())
                .when()
                .post("/employees")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .body("name", equalTo("Vinay"))
                .body("email", equalTo("vinay@gmail.com"))
                .body("department", equalTo("QA"));
    }

    // Test 4: Update employee
    @Test(priority = 4)
    public void updateEmployeeTest() {

        given()
                .spec(requestSpec)
                .pathParam("id", 1)
                .body(EmployeeTestData.updatedEmployee())
                .when()
                .put("/employees/{id}")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("John Updated"))
                .body("email", equalTo("johnupdated@gmail.com"))
                .body("department", equalTo("Automation QA"));
    }

    // Test 5: API chaining (POST -> Extract ID -> GET)
    @Test(priority = 5)
    public void createAndGetEmployeeTest() {

        int employeeId =
                given()
                        .spec(requestSpec)
                        .body(EmployeeTestData.chainingEmployee())
                        .when()
                        .post("/employees")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        logger.info("Created Employee ID: {}", employeeId);

        given()
                .spec(requestSpec)
                .pathParam("id", employeeId)
                .when()
                .get("/employees/{id}")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("id", equalTo(employeeId))
                .body("name", equalTo("Vinay Chaining"))
                .body("email", equalTo("vinay.chaining@gmail.com"))
                .body("department", equalTo("SDET"));
    }

    // Test 6: Get employee with invalid ID
    @Test(priority = 6)
    public void getEmployeeWithInvalidIdTest() {

        given()
                .spec(requestSpec)
                .pathParam("id", 999)
                .when()
                .get("/employees/{id}")
                .then()
                .statusCode(404)
                .body("message", equalTo("Employee not found"));
    }

    // Test 7: Create employee without name
    @Test(priority = 7)
    public void createEmployeeWithoutNameTest() {

        given()
                .spec(requestSpec)
                .body(EmployeeTestData.employeeWithoutName())
                .when()
                .post("/employees")
                .then()
                .statusCode(400);
    }

    // Test 8: Create employee with invalid email
    @Test(priority = 8)
    public void createEmployeeWithInvalidEmailTest() {

        given()
                .spec(requestSpec)
                .body(EmployeeTestData.employeeWithInvalidEmail())
                .when()
                .post("/employees")
                .then()
                .statusCode(400);
    }

    // Test 9: Update employee with invalid ID
    @Test(priority = 9)
    public void updateEmployeeWithInvalidIdTest() {

        given()
                .spec(requestSpec)
                .pathParam("id", 999)
                .body(EmployeeTestData.employeeForInvalidUpdate())
                .when()
                .put("/employees/{id}")
                .then()
                .statusCode(404)
                .body("message", equalTo("Employee not found"));
    }
}
