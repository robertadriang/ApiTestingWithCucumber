package stepImplementation;


import io.cucumber.messages.internal.com.google.protobuf.Api;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.ApiResponse;
import models.Specialty;
import rest.Endpoints;


public class SpecialtyStepImpl {

  public void getSpecialties() {
    ApiResponse.response=RestAssured.get(Endpoints.BASE_URL+Endpoints.SPECIALTIES);
    ApiResponse.saveStringAndStatusCode();

  }

  public void updateSpecialty(String newSpecialtyName,String method) {
    ApiResponse.createdSpecialty.setName(newSpecialtyName);
    if(method.equals("POST")){
      CommonStepImpl.postRequest(ContentType.JSON,ApiResponse.createdSpecialty,Endpoints.BASE_URL+Endpoints.SPECIALTIES);
    }else if(method.equals("PUT")){
      ApiResponse.response= RestAssured.given()
              .contentType(ContentType.JSON)
              .body(ApiResponse.createdSpecialty)
              .put(Endpoints.BASE_URL+Endpoints.SPECIALTIES+'/'+ApiResponse.createdSpecialty.getId());
    }else{
      throw new Error("Something is wrong");
    }
    ApiResponse.saveStringAndStatusCode();
  }

  public void getSpecialtyById(int id) {
    ApiResponse.response=RestAssured.get(Endpoints.BASE_URL+Endpoints.SPECIALTIES+'/'+id);
    ApiResponse.saveStringAndStatusCode();
  }

  public void createSpecialty(String specialtyName) {
    ApiResponse.createdSpecialty=new Specialty(null,specialtyName);
    CommonStepImpl.postRequest(ContentType.JSON,ApiResponse.createdSpecialty,Endpoints.BASE_URL+Endpoints.SPECIALTIES);
    ApiResponse.saveStringAndStatusCode();
  }

  public void deleteSpecialtyById(Integer id) {
    CommonStepImpl.deleteRequest(Endpoints.BASE_URL+Endpoints.SPECIALTIES+'/'+id);
    ApiResponse.saveStringAndStatusCode();
  }
}
