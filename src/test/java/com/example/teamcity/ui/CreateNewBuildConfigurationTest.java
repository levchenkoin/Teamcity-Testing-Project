package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.pages.admin.CreateNewBuildConfiguration;
import com.example.teamcity.ui.pages.admin.EditProject;
import com.example.teamcity.ui.pages.favorites.ProjectsPage;
import com.example.teamcity.ui.pages.project.MainPage;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

public class CreateNewBuildConfigurationTest extends BaseUiTest{
    private String url = "https://github.com/levchenkoin/exploring_page_object";
    @Test
    public void authorizedUserShouldBeAbleToCreateNewBuildConfiguration(){
        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());

        new CheckedProject(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .create(testData.getProject());

        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(testData.getProject().getName()));

        new EditProject().open(testData.getProject().getId())
                .createNewBuildConfigurationButtonClick();

        new CreateNewBuildConfiguration()
                .open(testData.getProject().getId())
                .createBuildConfigurationByUrl(url)
                .setupBuildConfiguration(testData.getBuildType().getName());

        new MainPage().open(testData.getProject().getId())
                .getBuildTypes()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(testData.getBuildType().getName()));

        var buildConfig = new CheckedBuildConfig(Specifications.getSpec().authSpec(testData.getUser()))
                 .get(testData.getBuildType().getName());
        softy.assertThat(buildConfig.getId()).isNotEmpty();
        softy.assertThat(testData.getBuildType().getName()).isEqualTo(buildConfig.getName());

    }

    @Test
    public void authorizedUserShouldNotBeAbleToCreateNewBuildConfigurationWithoutAName(){
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/levchenkoin/exploring_page_object";
        var buildConfDescription = testData.getBuildType();
        buildConfDescription.setName("");

        loginAsUser(testData.getUser());

        new CheckedProject(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .create(testData.getProject());

        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(testData.getProject().getName()));

        new EditProject().open(testData.getProject().getId())
                .createNewBuildConfigurationButtonClick();

        new CreateNewBuildConfiguration()
                .open(testData.getProject().getId())
                .createBuildConfigurationByUrl(url)
                .setupWithError(buildConfDescription.getName());

        new UncheckedBuildConfig(Specifications.getSpec().authSpec(testData.getUser()))
                .get(testData.getBuildType().getName())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
