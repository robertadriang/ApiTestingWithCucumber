package stepImplementation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.ApiResponse;
import models.PetType;
import models.Specialty;
import rest.Endpoints;

public class PetTypeStepImpl {
    public void getPetTypes() {
        ApiResponse.response= RestAssured.get(Endpoints.BASE_URL+Endpoints.PET_TYPES);
        ApiResponse.saveStringAndStatusCode();
    }

    public void createPetType(String petName) {
        ApiResponse.createdPetType=new PetType(null,petName);
        CommonStepImpl.postRequest(ContentType.JSON,ApiResponse.createdPetType,Endpoints.BASE_URL+Endpoints.PET_TYPES);
        ApiResponse.saveStringAndStatusCode();
    }

    public void updatePetType(String newPetType, String method) {
        if(method.equals("POST")){
            ApiResponse.createdPetType.setName(newPetType);
            CommonStepImpl.postRequest(ContentType.JSON,ApiResponse.createdPetType,Endpoints.BASE_URL+Endpoints.PET_TYPES);
        }else if(method.equals("PUT")){
            ApiResponse.createdPetType.setName(newPetType);
            ApiResponse.response= RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(ApiResponse.createdPetType)
                    .put(Endpoints.BASE_URL+Endpoints.PET_TYPES+'/'+ApiResponse.createdPetType.getId());
        }else{
            throw new Error("Something is wrong");
        }
        ApiResponse.saveStringAndStatusCode();
    }

    public void getPetTypeById(int id) {
        ApiResponse.response=RestAssured.get(Endpoints.BASE_URL+Endpoints.PET_TYPES+'/'+id);
        ApiResponse.saveStringAndStatusCode();
    }

    public void deletePetTypeById(Integer id) {
        CommonStepImpl.deleteRequest(Endpoints.BASE_URL+Endpoints.PET_TYPES+'/'+id);
        ApiResponse.saveStringAndStatusCode();
    }
}
