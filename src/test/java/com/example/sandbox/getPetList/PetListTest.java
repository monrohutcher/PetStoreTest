package com.example.sandbox.getPetList;

import com.example.sandbox.Common;
import com.example.sandbox.TestService;
import com.example.sandbox.util.constans.PetStatus;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

public class PetListTest extends Common {

    TestService petTestService = new TestService();
    String invalidStatusName = "reserved";

    @Test(enabled = true,groups = {SMOKE, REGRESSION},description ="Test case for validating GET /pet/findByStatus?status=available call")
    public void Test_GetAvailablePetList(){
        Response response = petTestService.getPetsByStatus(PetStatus.AVAILABLE);
        Assert.assertEquals(response.getStatusCode(),200, "Unexpected response code");
        Assert.assertEquals(response.getBody().jsonPath().get("[0].status"), PetStatus.AVAILABLE, "Pets with invalid status returned");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");
    }

    @Test(enabled = true,groups = {REGRESSION},description ="Test case for validating GET /pet/findByStatus?status=pending call")
    public void Test_GetPendingPetList(){
        Response response = petTestService.getPetsByStatus(PetStatus.PENDING);
        Assert.assertEquals(response.getStatusCode(),200, "Unexpected response code");
        Assert.assertEquals(response.getBody().jsonPath().get("[0].status"), PetStatus.PENDING, "Pets with invalid status returned");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");
    }

    @Test(enabled = true,groups = {REGRESSION},description ="Test case for validating GET /pet/findByStatus?status=sold call")
    public void Test_GetSoldPetList(){
        Response response = petTestService.getPetsByStatus(PetStatus.SOLD);
        Assert.assertEquals(response.getStatusCode(),200, "Unexpected response code");
        Assert.assertEquals(response.getBody().jsonPath().get("[0].status"), PetStatus.SOLD, "Pets with invalid status returned");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");
    }

    @Test(enabled = true,groups = {SMOKE, REGRESSION},description ="Test case for validating GET /pet/findByStatus?status=available,pending,sold call")
    public void Test_GetAllPetList(){
        Response response = petTestService.getPetsByStatus(PetStatus.statusList);
        Assert.assertEquals(response.getStatusCode(),200, "Unexpected response code");
        Assert.assertTrue(PetStatus.statusList.contains(response.getBody().jsonPath().get("[0].status")), "Pets with invalid status returned");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");
    }

    // NOTE: Endpoint returns with 200 status code and [] empty response
    // but according to swagger, it should return with 400 status code,
    // therefore this test is failing.
    @Test(enabled = true,groups = {REGRESSION},description = "Test case for validating GET /pet/findByStatus?status=reserved call as non existing status")
    public void Test_GetPetListWithInvalidStatus(){
        Response response = petTestService.getPetsByStatus(invalidStatusName);
        Assert.assertEquals(response.getStatusCode(),400, "Unexpected response code");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");

    }

    @Test(enabled = true,groups = {REGRESSION},description = "Test case for validating GET /pet/findByStatus call without query parameters")
    public void Test_GetPetListWithoutStatus(){
        Response response = petTestService.getPetsByStatus();
        Assert.assertEquals(response.getStatusCode(),200, "Unexpected response code");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");

    }
}
