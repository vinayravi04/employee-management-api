package com.sdet.employeemanagement.testdata;

public class EmployeeTestData {

    // Valid employee data
    public static String validEmployee() {

        return """
                {
                    "name": "Vinay",
                    "email": "vinay@gmail.com",
                    "department": "QA",
                    "salary": 85000
                }
                """;
    }

    // Employee data for API chaining
    public static String chainingEmployee() {

        return """
                {
                    "name": "Vinay Chaining",
                    "email": "vinay.chaining@gmail.com",
                    "department": "SDET",
                    "salary": 90000
                }
                """;
    }

    // Employee data with updated details
    public static String updatedEmployee() {

        return """
                {
                    "name": "John Updated",
                    "email": "johnupdated@gmail.com",
                    "department": "Automation QA",
                    "salary": 95000
                }
                """;
    }

    // Employee data without name
    public static String employeeWithoutName() {

        return """
                {
                    "email": "missingname@gmail.com",
                    "department": "QA",
                    "salary": 85000
                }
                """;
    }

    // Employee data with invalid email
    public static String employeeWithInvalidEmail() {

        return """
                {
                    "name": "Test User",
                    "email": "invalid-email",
                    "department": "QA",
                    "salary": 85000
                }
                """;
    }

    // Employee data for invalid ID update
    public static String employeeForInvalidUpdate() {

        return """
                {
                    "name": "Test User",
                    "email": "testuser@gmail.com",
                    "department": "QA",
                    "salary": 85000
                }
                """;
    }
}
