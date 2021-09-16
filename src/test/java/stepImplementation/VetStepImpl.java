package stepImplementation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.ApiResponse;
import models.Specialty;
import models.Vet;
import rest.Endpoints;

import java.util.ArrayList;
import java.util.List;

public class VetStepImpl {
    public void getVets() {
        ApiResponse.response= RestAssured.get(Endpoints.BASE_URL+Endpoints.VETS);
        ApiResponse.saveStringAndStatusCode();
    }

    public void createVet(String firstName, String lastName) {
        ApiResponse.createdVet=new Vet(null,firstName,lastName,new ArrayList<>());
        CommonStepImpl.postRequest(ContentType.JSON,ApiResponse.createdVet,Endpoints.BASE_URL+Endpoints.VETS);
        ApiResponse.saveStringAndStatusCode();
    }

    public void createVet(String firstName, String lastName,List<Specialty> specialties) {
        ApiResponse.createdVet=new Vet(null,firstName,lastName,specialties);
        CommonStepImpl.postRequest(ContentType.JSON,ApiResponse.createdVet,Endpoints.BASE_URL+Endpoints.VETS);
        ApiResponse.saveStringAndStatusCode();
    }

    public void updateVet( String method) {
        if(method.equals("POST")){
            CommonStepImpl.postRequest(ContentType.JSON,ApiResponse.createdVet,Endpoints.BASE_URL+Endpoints.VETS);
        }else if(method.equals("PUT")){
            ApiResponse.response= RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(ApiResponse.createdVet)
                    .put(Endpoints.BASE_URL+Endpoints.VETS+'/'+ApiResponse.createdVet.getId());
        }else{
            throw new Error("Something is wrong");
        }
        ApiResponse.saveStringAndStatusCode();
    }

    public void getVetById(int id) {
        ApiResponse.response=RestAssured.get(Endpoints.BASE_URL+Endpoints.VETS+'/'+id);
        ApiResponse.saveStringAndStatusCode();
    }

    public void deleteVetById(Integer id) {
        CommonStepImpl.deleteRequest(Endpoints.BASE_URL+Endpoints.VETS+'/'+id);
        ApiResponse.saveStringAndStatusCode();
    }
}
