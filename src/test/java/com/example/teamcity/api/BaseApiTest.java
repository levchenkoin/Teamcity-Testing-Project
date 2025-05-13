package com.example.teamcity.api;
<<<<<<< HEAD

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import lombok.Getter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

@Getter
public class BaseApiTest extends BaseTest {
    public TestDataStorage testDataStorage;

    public CheckedRequests checkedWithSuperUser
            = new CheckedRequests(Specifications.getSpec().superUserSpec());

    public UncheckedRequests uncheckedWithSuperUser
            = new UncheckedRequests(Specifications.getSpec().superUserSpec());

    @BeforeMethod
    public void setupTest() {

        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void cleantest() {

        testDataStorage.delete();
    }
=======
public class BaseApiTest extends BaseTest {
>>>>>>> hometask-4
}