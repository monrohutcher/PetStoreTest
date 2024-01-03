package com.example.sandbox.businessProcesses;

import com.example.sandbox.Common;
import com.example.sandbox.TestService;
import com.example.sandbox.util.constans.PetStatus;
import com.example.sandbox.util.constans.TestData;
import com.example.sandbox.util.swagger.definitions.Item;
import com.example.sandbox.util.swagger.definitions.PetBody;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

public class PetLifeCycle extends Common {

    TestService testService = new TestService();
    PetBody testPetBodyObject = testService.buildPetBody(
            TestData.NAME,
            TestData.DEFAULT_ITEM,
            List.of(TestData.HYDRAIMAGE),
            Collections.singletonList(TestData.DEFAULT_ITEM),
            PetStatus.PENDING
    );
    String testPetBodyString = testService.getJsonStringFromObject(testPetBodyObject);;
    PetBody modifiedTestPetBodyObject = testService.modifyPetBody(
            testPetBodyObject.getId(),
            testPetBodyObject.getName(),
            testPetBodyObject.getCategory(),
            testPetBodyObject.getPhotoUrls(),
            testPetBodyObject.getTags(),
            PetStatus.AVAILABLE);
    String modifiedTestPetBodyString = testService.getJsonStringFromObject(modifiedTestPetBodyObject);

    @Test(priority = 0, enabled = true, groups = {SMOKE, REGRESSION},description ="Test for POST /pet call to create new pet entity")
    public void Test_PostNewPetToStore(){
        Response response = postUrl(newPet, testPetBodyString);

        Assert.assertEquals(response.getStatusCode(),200, "Unexpected response code");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");

        String retrievedPetDetails = testService.getPetsByID(testPetBodyObject.getId()).getBody().asString();
        Assert.assertEquals(retrievedPetDetails, testPetBodyString, "Invalid pet details returned");
    }

    @Test(priority = 1, enabled = true,groups = {SMOKE, REGRESSION},description ="Test for PUT /pet call to modify already created pet entity")
    public void Test_PutUpdatePet(){
        Response response = putUrl(newPet, modifiedTestPetBodyString);

        Assert.assertEquals(response.getStatusCode(),200, "Unexpected response code");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");

        String retrievedPetDetails = testService.getPetsByID(modifiedTestPetBodyObject.getId()).getBody().asString();
        Assert.assertEquals(retrievedPetDetails, modifiedTestPetBodyString, "Invalid pet details returned");

    }

    @Test(priority = 2, enabled = true,groups = {SMOKE, REGRESSION},description ="Test for DELETE /pet/{petId} call to delete the created pet entity")
    public void Test_DeleteCreatedPet(){
        Response response = testService.deletePetsByID(modifiedTestPetBodyObject.getId());

        Assert.assertEquals(response.getStatusCode(),200, "Unexpected response code");
        Assert.assertEquals(response.getBody().jsonPath().get("code").toString(),"200", "Unexpected code in response body");
        Assert.assertEquals(response.getBody().jsonPath().get("type"),"unknown", "Unexpected type in response body");
        Assert.assertEquals(response.getBody().jsonPath().get("message"),String.valueOf(modifiedTestPetBodyObject.getId()), "Unexpected message in response body");
        Assert.assertEquals(response.getContentType(),"application/json", "Unexpected content type");

        Response getResponse = testService.getPetsByID(modifiedTestPetBodyObject.getId());
        Assert.assertEquals(getResponse.getStatusCode(),404, "Unexpected response code");
        Assert.assertEquals(getResponse.getBody().jsonPath().get("code").toString(),"1", "Unexpected code in response body");
        Assert.assertEquals(getResponse.getBody().jsonPath().get("type"),"error", "Unexpected type in response body");
        Assert.assertEquals(getResponse.getBody().jsonPath().get("message"),"Pet not found", "Unexpected message in response body");    }


}
