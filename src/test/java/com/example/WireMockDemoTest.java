package com.example;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class WireMockDemoTest {


    private static final String HOST = "localhost";
    private static final int PORT = 8081;
    private static WireMockServer server = new WireMockServer(PORT);

    @BeforeClass
    public void initializeServer() {

        System.out.println("Init server");

        server.start();
        WireMock.configureFor(HOST, PORT);

        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
        mockResponse.withStatus(201);
        mockResponse.withStatusMessage("Hello from Mock");
        mockResponse.withHeader("Content-Type", "text/json");
        mockResponse.withHeader("token", "123456");
        mockResponse.withHeader("Set-Cookie", "session_id=91837492837");
        mockResponse.withHeader("Set-Cookie", "split_test_group=WireMockDemo");
        mockResponse.withBody("Response from wire mock demo class");

        WireMock.stubFor(WireMock.get("/student/1").willReturn(mockResponse));
    }

    @Test
    public void testMockDemo() {
        String testApi = "http://localhost:" + PORT + "/student/1";
        System.out.println("Service to be hit : " + testApi);

        Response response = RestAssured
                .given()
                .get("http://localhost:8081/student/1")
                .then()
                .statusCode(201)
                .extract()
                .response();

        System.out.println("------------");

        Assert.assertEquals(response.getHeader("token"), "123456");
        Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 201 Hello from Mock");
        Assert.assertEquals(response.getCookie("session_id"), "91837492837");
        Assert.assertEquals(response.getCookie("split_test_group"), "WireMockDemo");
        Assert.assertEquals(response.getBody().asString(), "Response from wire mock demo class");
    }

    @AfterClass
    public void ShutdownServer() {
        if (server.isRunning() && null != server) {
            System.out.println("Shut Down");
            server.shutdown();
        }
    }

}