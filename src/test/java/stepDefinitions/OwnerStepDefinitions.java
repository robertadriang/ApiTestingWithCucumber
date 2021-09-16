package stepDefinitions;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Owner;
import models.Pet;
import models.Type;
import models.Visit;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.lang3.StringUtils;
import stepImplementation.OwnerStepImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;


public class OwnerStepDefinitions {

  String responseString;

  Owner owner = new Owner();

  Owner createdOwner;

  Response response;

  @Steps
  OwnerStepImpl ownerImpl;


  @Given("The user get the list with all owners")
  public void getOwners () {

    responseString = RestAssured.
        given().
        when().get("http://localhost:9966/petclinic/api/owners").
        then().assertThat().statusCode(201)
        .extract().body().asString();
  }


  @Then("A list of owners is returned")
  public void aListOfOwnersIsReturned () {

    assertTrue(StringUtils.isNotEmpty(responseString));
  }


  @When("The user creates a new owner")
  public void theUserCreatesANewOwner (List<Map<String, String>> table) {

    for (Map<String, String> columns : table) {
      List<Pet> pets = new ArrayList<>();
      Pet pet = new Pet();
      Type type = new Type();
      pet.setName(columns.get("petName"));
      pet.setType(type);
      List<Visit> visits = new ArrayList<>();
      Visit visit = new Visit();
      visits.add(visit);
      pet.setVisits(visits);
      pets.add(pet);

      this.owner = Owner.builder().lastName(columns.get("lastName"))
          .address(columns.get("address"))
          .city(columns.get("city"))
          .firstName(columns.get("firstName"))
          .telephone(columns.get("telephone"))
          .pets(pets)
          .build();
      RestAssured.given()
          .contentType(ContentType.JSON)
          .body(owner)
          .when()
          .post("http://localhost:9966/petclinic/api/owners")
          .then().log().all().statusCode(201);
    }
  }


  @When("The user creates owner with firstName {} and lastName {}")
  public void createNewSpecificUser (String firstName, String lastName) {

    ownerImpl.createNewOwner(firstName, lastName);
  }


  @Then("The {} {} owner is in the response")
  public void verifyCreatedUserInTheList (String firstName, String lastName) {

    ownerImpl.verifyCreatedOwner(firstName, lastName);
  }


  @Then("The new {} {} owner is in the response")
  public void verifyCreatedUserInTheList2 (String firstName, String lastName) {

    assertTrue(responseString.contains(firstName));
    assertTrue(responseString.contains(lastName));
  }


  @Then("The {} owner is created")
  public void verifyCreatedOwner (String lastName) {

    responseString = RestAssured.
        given()
        .pathParam("lastName", lastName)
        .when().get("http://localhost:9966/petclinic/api/owners/*/lastname/{lastName}")
        .then().assertThat().statusCode(200)
        .extract().body().asString();
  }


  @When("The user creates a new owner with firstName {} and lastName {} second example")
  public void createNewSpecificUser2 (String firstName, String lastName) {

    owner = new Owner();
    List<Pet> pets = new ArrayList<>();
    Pet pet = new Pet();
    Type type = new Type();
    List<Visit> visits = new ArrayList<>();
    Visit visit = new Visit();
    visits.add(visit);
    pet.setVisits(visits);
    pet.setType(type);
    pets.add(pet);
    owner.setFirstName(firstName);
    owner.setLastName(lastName);
    createdOwner = RestAssured.given()
        .contentType(ContentType.JSON)
        .body(owner)
        .when()
        .post("http://localhost:9966/petclinic/api/owners")
        .then().log().all().statusCode(201).extract().body().as(Owner.class);
  }


  @And("The new {} owner is created - second example")
  public void theLastNameOwnerIsCreatedSecondExample (String lastName) {

    assertTrue(createdOwner.getFirstName().equals(owner.getFirstName()));
  }
}
