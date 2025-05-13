package com.example.teamcity.api;

<<<<<<< HEAD
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.checked.CheckedUser;
=======
import com.example.teamcity.api.requests.CheckedRequests;
>>>>>>> hometask-4
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseApiTest {
    @Test
    public void buildConfigurationTest() {
        var testData = testDataStorage.addTestData();
<<<<<<< HEAD

        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(testData.getUser());

        var project = new CheckedProject(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }
=======
        CheckedRequests checkedRequests;

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        checkedRequests = new CheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()));

        checkedRequests
                .getProjectRequest()
                .create(testData.getProject());

        var buildConfig = checkedRequests
                .getBuildConfigRequest()
                .create(testData.getBuildType());

        softy.assertThat(buildConfig.getId())
                .isEqualTo(testData.getBuildType()
                        .getId());
    }

//        new CheckedUser(Specifications.getSpec().superUserSpec())
//                .create(testData.getUser());
//
//        var project = new CheckedProject(Specifications.getSpec()
//                .authSpec(testData.getUser()))
//                .create(testData.getProject());
//
//        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
//    }
>>>>>>> hometask-4
}