package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.Page;

import static com.codeborne.selenide.Selenide.$;

public class EditProject extends Page {
    private static final String EDIT_PROJECT_URL = "/admin/editProject.html?projectId=";

    private SelenideElement createBuildConfigurationButton = $("[class='btn'][href*='createBuildTypeMenu']");

    public EditProject open(String projectId){

        Selenide.open(EDIT_PROJECT_URL + projectId);
        waitUntilPageIsUpdated();
        return this;
    }

    public void createNewBuildConfigurationButtonClick(){
        createBuildConfigurationButton.scrollIntoView(true).click();
        waitUntilPageContentIsLoaded();
    }
}
