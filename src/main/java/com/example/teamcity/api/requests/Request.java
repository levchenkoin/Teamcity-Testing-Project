package com.example.teamcity.api.requests;

import io.restassured.specification.RequestSpecification;

public class Request {
    protected RequestSpecification spec;

    public Request(RequestSpecification spec) {
        this.spec = spec;
    }
}