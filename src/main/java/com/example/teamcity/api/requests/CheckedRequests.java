package com.example.teamcity.api.requests;

<<<<<<< HEAD
=======
import com.example.teamcity.api.requests.checked.CheckedAgents;
>>>>>>> hometask-4
import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.checked.CheckedUser;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequests {
    private CheckedUser userRequest;
    private CheckedProject projectRequest;
    private CheckedBuildConfig buildConfigRequest;
<<<<<<< HEAD
=======
    private CheckedAgents agentsRequest;

>>>>>>> hometask-4

    public CheckedRequests(RequestSpecification spec) {
        this.userRequest = new CheckedUser(spec);
        this.buildConfigRequest = new CheckedBuildConfig(spec);
        this.projectRequest = new CheckedProject(spec);
<<<<<<< HEAD
=======
        this.agentsRequest = new CheckedAgents(spec);

>>>>>>> hometask-4
    }
}
