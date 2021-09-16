package stepDefinitions;

import com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.ApiResponse;
import models.PetType;
import models.Specialty;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.lang3.StringUtils;
import stepImplementation.PetTypeStepImpl;

import static org.junit.Assert.*;

public class PetTypeStepDefinitions {

    @Steps
    PetTypeStepImpl petTypeStepImpl;

    Gson gson=new Gson();

    @Given("I have at least one pet type returned from the API")
    public void iHaveAtLeastOnePetTypeReturnedFromTheAPI() {
        petTypeStepImpl.getPetTypes();
        assertTrue(StringUtils.isNotEmpty(ApiResponse.responseString));
    }

    @When("I request all pet types from the API")
    public void iRequestAllPetTypesFromTheAPI() {
        petTypeStepImpl.getPetTypes();
    }


    @When("I create a new petType with the name {}")
    public void iCreateANewPetTypeWithTheNamePetTypeName(String petName) {
        if(petName.equals("TODAY_TIME")){
            petName=java.time.LocalDateTime.now().toString();
        }
        petTypeStepImpl.createPetType(petName);
    }

    @Then("the pet types returned from the API contain the {}")
    public void thePetTypesFromReturnedFromTheAPIContainTheCreatedPetType(String type) {
        petTypeStepImpl.getPetTypes();
        if(ApiResponse.createdPetType.getId()!=null){
            assertTrue(ApiResponse.responseString.contains(gson.toJson(ApiResponse.createdPetType, PetType.class)));
        }else{
            assertTrue(ApiResponse.responseString.contains(ApiResponse.createdPetType.getName()));
        }
    }

    @When("I try to update the first petType returned by the API in {} by {}")
    public void iTryToUpdateTheFirstPetTypeReturnedByTheAPIInEditedPetTypeByPOST(String newPetType, String method) {
        ApiResponse.initialPetType= gson.fromJson(ApiResponse.responseString, PetType[].class)[0];
        ApiResponse.createdPetType=gson.fromJson(ApiResponse.responseString, PetType[].class)[0];
        petTypeStepImpl.updatePetType(newPetType,method);
    }

    @When("I request the first petType returned by the API")
    public void iRequestTheFirstPetTypeReturnedByTheAPI() {
        petTypeStepImpl.getPetTypes();
        int firstId=gson.fromJson(ApiResponse.responseString,PetType[].class)[0].getId();
        petTypeStepImpl.getPetTypeById(firstId);
    }

    @Then("the response contains a petType")
    public void theResponseContainsAPetType() {
        ApiResponse.initialPetType= gson.fromJson(ApiResponse.responseString, PetType.class);
        ApiResponse.createdPetType=ApiResponse.initialPetType;
        assertNotNull(ApiResponse.initialPetType);
    }

    @When("I try to DELETE the last petType returned by the API")
    public void iTryToDELETETheLastPetTypeReturnedByTheAPI() {
        PetType[] petTypes=gson.fromJson(ApiResponse.responseString, PetType[].class);
        ApiResponse.deletedPetType=petTypes[petTypes.length-1];
        petTypeStepImpl.deletePetTypeById(ApiResponse.deletedPetType.getId());
    }

    @Then("the pet types returned from the API don't contain the {}")
    public void thePetTypesReturnedFromTheAPIDonTContainTheDeletedPetType(String type) {
        petTypeStepImpl.getPetTypes();
        assertFalse(ApiResponse.responseString.contains(gson.toJson(ApiResponse.deletedPetType,PetType.class)));
    }
}
