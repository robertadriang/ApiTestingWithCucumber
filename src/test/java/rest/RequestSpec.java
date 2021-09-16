package rest;


import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static net.serenitybdd.rest.SerenityRest.rest;


public class RequestSpec {

  public RequestSpecification spec () {

    return rest()
        .baseUri(Endpoints.BASE_URL)
        .contentType(ContentType.JSON);
  }
}
