package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.element;

public class CreateNewBuildConfiguration extends Page {

    private SelenideElement urlInput = element(Selectors.byId("url"));
    private SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));
    private SelenideElement errorBuildTypeName = element(Selectors.byId("error_buildTypeName"));

    public CreateNewBuildConfiguration open(String ProjectId){
        Selenide.open("/admin/createObjectMenu.html?projectId=" + ProjectId + "&showMode=createBuildTypeMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewBuildConfiguration createBuildConfigurationByUrl(String url){
        urlInput.shouldBe(Condition.visible, Duration.ofSeconds(30));
        urlInput.sendKeys(url);
        submit();
        waitUntilConnectionSuccessful();
        return this;
    }

    public void setupBuildConfiguration(String buildTypeName){
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);

        submit();
        waitUntilDataIsSaved();
        waitUntilPageIsUpdated();
    }

    public void setupWithError(String buildTypeName){
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);
        submit();
        errorBuildTypeName.shouldHave(text("Build configuration name must not be empty"));
    }
}
