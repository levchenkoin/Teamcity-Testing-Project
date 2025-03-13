package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.project.MainPage;
import lombok.Getter;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class StartUpPage extends MainPage {

    @Getter
    private SelenideElement header = element(Selectors.byId("header"));
    private SelenideElement proceedButton = element(Selectors.byId("proceedButton"));
    private SelenideElement acceptLisense = element(Selectors.byId("accept"));

    public StartUpPage open() {
        Selenide.open("/mnt");
        return this;
    }

    public StartUpPage startUpTeamcityServer() {
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        acceptLisense.shouldBe(Condition.enabled, Duration.ofMinutes(5));
        acceptLisense.scrollTo();
        acceptLisense.click();
        submit();
        return this;
    }
}