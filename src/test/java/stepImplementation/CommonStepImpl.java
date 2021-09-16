package stepImplementation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.ApiResponse;
import models.PetType;
import rest.Endpoints;

public class CommonStepImpl {
    public static void postRequest(ContentType contentType, Object body, String path) {
        ApiResponse.response= RestAssured.given()
                .contentType(contentType)
                .body(body)
                .post(path);
    }

    public static void deleteRequest(String path){
        ApiResponse.response=RestAssured.delete(path);
    }

}
