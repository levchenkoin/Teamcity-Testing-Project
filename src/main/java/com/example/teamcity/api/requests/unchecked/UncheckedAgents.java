package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class UncheckedAgents extends Request implements CrudInterface {

    private static final String AGENTS_ENDPOINT = "/app/rest/agents";

    public UncheckedAgents(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Object create(Object obj) {
        return null;
    }

    @Override
    public Response get(String locator) {
        return given().spec(spec)
                .param("locator", locator)
                .get(AGENTS_ENDPOINT);
    }

//    public Response get(String id) {
//        return null;
//    }

    @Override
    public Object update(String id, Object obj) {
        return null;
    }

    @Override
    public Object delete(String id) {
        return null;
    }

    public Response authorize(String agentId) {
        return given().spec(spec)
                .contentType("text/plain")
                .accept("*/*")
                .body("true")
                .put(format("%s/id:%s/authorized", AGENTS_ENDPOINT, agentId));
    }
}