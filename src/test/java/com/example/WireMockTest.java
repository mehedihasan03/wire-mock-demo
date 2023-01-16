package com.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static org.junit.jupiter.api.Assertions.*;

class WireMockTest {

    @Test
    public void testUser0(){

        RestAssured
                .given()
                .get("http://localhost:8080/user/0")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testUser1(){

        RestAssured
                .given()
                .get("http://localhost:8080/user/1")
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    public void testUser2(){

        String contentType = RestAssured
                .given()
                .get("http://localhost:8080/user/2")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .header("Content-Type");

        System.out.println("Test Response Header : " + contentType);
        Assert.assertEquals("text/plain", contentType);
    }

    @Test
    public void testUser3(){

        RestAssured
                .given()
                .get("http://localhost:8080/user/3")
                .then()
                .assertThat()
                .statusCode(203);
    }

}