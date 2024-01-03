package com.example.sandbox.getPet;

import com.example.sandbox.Common;
import com.example.sandbox.TestService;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

public class PetDetailTest extends Common {

    TestService petTestService = new TestService();
    Long validPetId;
    Long invalidPetId;
    String notSupportedId;

    @BeforeTest()
    private void prepareTestData() {
        notSupportedId = "a22";
        validPetId = petTestService.getValidPetId();
        invalidPetId = petTestService.getInvalidPetId();
    }

    @Test(enabled = true,groups = {SMOKE, REGRESSION},description = "Test case for validating GET /pet/{petId} with valid id")
    public void Test_GetPetByValidId() {
        Response response = petTestService.getPetsByID(validPetId);
        Assert.assertEquals(response.getStatusCode(),200, "Unexpected response code");
        Assert.assertEquals(response.getBody().jsonPath().get("id"),validPetId, "Invalid pet ID returned");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");
    }

    @Test(enabled = true,groups = {REGRESSION},description = "Test case for validating GET /pet/{petId} with invalid id")
    public void Test_GetPetByInvalidId() {
        Response response = petTestService.getPetsByID(invalidPetId);
        Assert.assertEquals(response.getStatusCode(),404, "Unexpected response code");
        Assert.assertEquals(response.getBody().jsonPath().get("code").toString(),"1", "Unexpected code in response body");
        Assert.assertEquals(response.getBody().jsonPath().get("type"),"error", "Unexpected type in response body");
        Assert.assertEquals(response.getBody().jsonPath().get("message"),"Pet not found", "Unexpected message in response body");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");
    }

    @Test(enabled = true,groups = {REGRESSION},description = "Test case for validating GET /pet/{petId} with not supported id")
    public void Test_GetPetWithNotSupportedId() {
        Response response = petTestService.getPetsByID(notSupportedId);
        Assert.assertEquals(response.getStatusCode(),404, "Unexpected response code");
        Assert.assertEquals(response.getBody().jsonPath().get("type"),"unknown", "Unexpected type in response body");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");
    }
}
