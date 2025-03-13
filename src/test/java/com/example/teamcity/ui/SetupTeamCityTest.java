package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.pages.StartUpPage;
import org.testng.annotations.Test;

public class SetupTeamCityTest extends BaseUiTest {

    @Test
    public void startUpTest() {
        new StartUpPage()
                .open()
                .startUpTeamcityServer()
                .getHeader().shouldHave(Condition.text("Create Administrator Account"));
    }
}
