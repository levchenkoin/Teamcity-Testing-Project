package com.example.teamcity.api;

import com.example.teamcity.api.models.Agent;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.awaitility.Awaitility.await;

public class SetupAgentTest extends BaseApiTest {

    @Test
    public void authorizeAgent() {
        var agentId = getUnauthorizedAgent().getId();
        softy.assertThat(checkedWithSuperUser.getAgentsRequest()
                        .authorize(agentId))
                .isEqualTo(true);
    }

    private Agent getUnauthorizedAgent() {
        var agents = new AtomicReference<List<Agent>>();
        await()
                .atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> {
                    agents.set(checkedWithSuperUser.getAgentsRequest()
                            .get("authorized:false")
                            .getAgent());
                    return !agents.get()
                            .isEmpty();
                });
        return agents.get()
                .get(0);
    }
}
