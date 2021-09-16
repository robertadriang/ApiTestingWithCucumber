package stepImplementation;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Owner;
import models.Pet;
import models.Type;
import models.Visit;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import rest.Endpoints;
import util.World;

import java.util.ArrayList;
import java.util.List;

import static net.serenitybdd.rest.SerenityRest.rest;
import static org.junit.Assert.assertEquals;


public class OwnerStepImpl {

  private Response response;

  Owner owner = new Owner();


  public void createNewOwner (String firstName, String lastName) {

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
    owner.setPets(pets);

    response = rest()
        .baseUri(Endpoints.BASE_URL)
        .contentType(ContentType.JSON)
        .body(owner)
        .post(Endpoints.OWNERS);

    Assert.assertEquals("create owner request failed", HttpStatus.SC_CREATED, response.statusCode());

    World.setResponse(response);
  }


  public void verifyCreatedOwner (String firstName, String lastName) {

    Owner createOwnerResponse = response.as(Owner.class);

    assertEquals("First name is incorrect", firstName, createOwnerResponse.getFirstName());
    assertEquals("Last name is incorrect", lastName, createOwnerResponse.getLastName());

    JsonPath jsonPath = response.jsonPath();


    assertEquals("First name is incorrect", firstName, jsonPath.getString("firstName"));
    assertEquals("Last name is incorrect", lastName, jsonPath.getString("lastName"));
  }
}
