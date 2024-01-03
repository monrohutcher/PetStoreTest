package com.example.sandbox.businessProcesses;

import com.example.sandbox.Common;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

public class PetLifeCycle extends Common {

    @BeforeSuite
    private void Setup_prepareTestData() {
        // Setup PetBody for testing
    }

    @Test(priority = 0, enabled = true,groups = {SMOKE, REGRESSION},description ="description")
    public void Test_PostNewPetToStore(){

    }

    @Test(priority = 1, enabled = true,groups = {SMOKE, REGRESSION},description ="description")
    public void Test_GetNewPet(){

    }

    @Test(priority = 2, enabled = true,groups = {SMOKE, REGRESSION},description ="description")
    public void Test_PutUpdatePet(){

    }

    @Test(priority = 3, enabled = true,groups = {SMOKE, REGRESSION},description ="description")
    public void Test_GetModifiedPet(){

    }

    @Test(priority = 4, enabled = true,groups = {SMOKE, REGRESSION},description ="description")
    public void Test_DeleteCreatedPet(){

    }

    @Test(priority = 5, enabled = true,groups = {SMOKE, REGRESSION},description ="description")
    public void Test_CheckDeletedPet(){

    }

}
