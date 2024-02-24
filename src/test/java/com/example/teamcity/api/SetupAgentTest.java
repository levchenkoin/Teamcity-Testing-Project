package com.example.teamcity.api;

import com.example.teamcity.api.requests.checked.CheckedAgents;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

import java.time.Duration;
import static org.awaitility.Awaitility.await;

public class SetupAgentTest extends BaseApiTest {
    @Test
    public void setupAgentTest() {
        //create new user and login data
        var testData = testDataStorage.addTestData();
        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());
        var checkedAgents = new CheckedAgents(Specifications.getSpec().authSpec(testData.getUser()));
        await().atMost(Duration.ofMinutes(5)).pollInterval(Duration.ofSeconds(5)).until(() -> !checkedAgents.getAllUnauthorizedAgents().getAgent().isEmpty());
        //get the list of unauthorized agents
        var allUnauthorizedAgents = checkedAgents
                .getAllUnauthorizedAgents();
        //make agent authorized
        var unauthorizedAgents = allUnauthorizedAgents.getAgent();
        softy.assertThat(unauthorizedAgents.size()).isGreaterThan(0);
        String agentName = unauthorizedAgents.get(0).getName();
        checkedAgents.updateAgentAuthorizationToTrue(agentName);

        //check that the agent is authorized
        await().atMost(Duration.ofMinutes(5)).pollInterval(Duration.ofSeconds(5)).until(() -> !checkedAgents.getAllAuthorizedAgents().getAgent().isEmpty());
        var allAuthorizedAgents = checkedAgents.getAllAuthorizedAgents();
        var authorizedAgents = allAuthorizedAgents.getAgent();

        softy.assertThat(authorizedAgents.get(0).getName()).isEqualTo(agentName);
    }
}
