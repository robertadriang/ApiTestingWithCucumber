package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import models.ApiResponse;

import static org.junit.Assert.assertEquals;

public class CommonStepDefinitions {
    @Given("The PetClinic application is up and running")
    public void verifyTheApplication() {
        RestAssured.
                given()
                .when()
                .get("http://localhost:9966/petclinic/swagger-ui.html#/owner-rest-controller")
                .then()
                .assertThat().statusCode(200);
    }

    @And("the status code is {}.")
    public void theStatusCodeIs(int statusCode) {
        assertEquals(statusCode, ApiResponse.statusCode);
    }
}
