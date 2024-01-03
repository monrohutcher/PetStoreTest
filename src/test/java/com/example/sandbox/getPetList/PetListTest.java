package com.example.sandbox.getPetList;

import com.example.sandbox.Common;
import com.example.sandbox.TestService;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

public class PetListTest extends Common {

    TestService petTestService = new TestService();
    String availableStatusName = "available";
    String pendingStatusName = "pending";
    String soldStatusName = "sold";
    List<String> statusList = Arrays.asList(
            availableStatusName,
            pendingStatusName,
            soldStatusName
    );
    String invalidStatusName = "reserved";

    @Test(enabled = true,groups = {SMOKE, REGRESSION},description ="description")
    public void Test_GetAvailablePetList(){
        Response response = petTestService.getPetsByStatus(availableStatusName);
        Assert.assertEquals(response.getStatusCode(),200, "Unexpected response code");
        Assert.assertEquals(response.getBody().jsonPath().get("[0].status"), availableStatusName, "Pets with invalid status returned");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");
    }

    @Test(enabled = true,groups = {REGRESSION},description ="description")
    public void Test_GetPendingPetList(){
        Response response = petTestService.getPetsByStatus(pendingStatusName);
        Assert.assertEquals(response.getStatusCode(),200, "Unexpected response code");
        Assert.assertEquals(response.getBody().jsonPath().get("[0].status"), pendingStatusName, "Pets with invalid status returned");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");
    }

    @Test(enabled = true,groups = {REGRESSION},description ="description")
    public void Test_GetSoldPetList(){
        Response response = petTestService.getPetsByStatus(soldStatusName);
        Assert.assertEquals(response.getStatusCode(),200, "Unexpected response code");
        Assert.assertEquals(response.getBody().jsonPath().get("[0].status"), soldStatusName, "Pets with invalid status returned");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");
    }

    @Test(enabled = true,groups = {SMOKE, REGRESSION},description ="description")
    public void Test_GetAllPetList(){
        Response response = petTestService.getPetsByStatus(statusList);
        Assert.assertEquals(response.getStatusCode(),200, "Unexpected response code");
        Assert.assertTrue(statusList.contains(response.getBody().jsonPath().get("[0].status")), "Pets with invalid status returned");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");
    }

    @Test(enabled = true,groups = {REGRESSION},description ="description")
    public void Test_GetPetListWithInvalidStatus(){
        Response response = petTestService.getPetsByStatus(invalidStatusName);
        Assert.assertEquals(response.getStatusCode(),400, "Unexpected response code");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");

    }

    @Test(enabled = true,groups = {REGRESSION},description ="description")
    public void Test_GetPetListWithoutStatus(){
        Response response = petTestService.getPetsByStatus();
        Assert.assertEquals(response.getStatusCode(),404, "Unexpected response code");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");

    }
}
