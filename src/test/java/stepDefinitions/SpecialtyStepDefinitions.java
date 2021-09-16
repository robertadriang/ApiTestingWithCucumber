package stepDefinitions;

import com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.ApiResponse;
import models.Specialty;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.lang3.StringUtils;
import stepImplementation.SpecialtyStepImpl;

import static org.junit.Assert.*;

public class SpecialtyStepDefinitions {
        @Steps
        SpecialtyStepImpl specialtyStepImpl;

    Gson gson=new Gson();

    @Given("I have at least one specialty returned from the API")
    public void iHaveAtLeastOneSpecialtyReturnedFromTheAPI() {
        specialtyStepImpl.getSpecialties();
        assertTrue(StringUtils.isNotEmpty(ApiResponse.responseString));
    }

    @When("I request all specialties from the API")
    public void iRequestAllSpecialtiesFromTheAPI() {
        specialtyStepImpl.getSpecialties();
    }

    @When("I try to update the first specialty returned by the API in {} by {}")
    public void iTryToUpdateTheFirstSpecialtyReturnedByTheAPIInEditedSpecialty(String newSpecialtyName, String method) {

        ApiResponse.initialSpecialty= gson.fromJson(ApiResponse.responseString, Specialty[].class)[0];
        ApiResponse.createdSpecialty=gson.fromJson(ApiResponse.responseString, Specialty[].class)[0];
        specialtyStepImpl.updateSpecialty(newSpecialtyName,method);
    }

    @Then("the specialties returned from the API contain the {}")
    public void theSpecialtiesReturnedFromTheAPIContainTheEditedSpecialty(String type) {
        specialtyStepImpl.getSpecialties();
        if(ApiResponse.createdSpecialty.getId()!=null){
            assertTrue(ApiResponse.responseString.contains(gson.toJson(ApiResponse.createdSpecialty,Specialty.class)));
        }else{
            assertTrue(ApiResponse.responseString.contains(ApiResponse.createdSpecialty.getName()));
        }
    }

    @When("I request the first specialty returned by the API")
    public void iRequestTheFirstSpecialtyReturnedByTheAPI() {
        specialtyStepImpl.getSpecialties();
        int firstId=gson.fromJson(ApiResponse.responseString,Specialty[].class)[0].getId();
        specialtyStepImpl.getSpecialtyById(firstId);
    }

    @Then("the response contains a specialty")
    public void theResponseContainsASpecialty() {
        ApiResponse.initialSpecialty= gson.fromJson(ApiResponse.responseString, Specialty.class);
        ApiResponse.createdSpecialty=ApiResponse.initialSpecialty;
        assertNotNull(ApiResponse.initialSpecialty); /// ApiResponse.initialSpecialty is of specialty type sspecialtyStepImpl = nullo we only need to check that it is not null
    }

    @When("I create a new specialty with the name {}")
    public void iCreateANewSpecialtyWithTheNameSpecialtyName(String specialtyName) {
        if(specialtyName.equals("TODAY_TIME")){
            specialtyName=java.time.LocalDateTime.now().toString();
        }
        specialtyStepImpl.createSpecialty(specialtyName);
    }

    @Then("the specialties returned from the API contain an entry with {}")
    public void theSpecialtiesReturnedFromTheAPIContainAnEntryWithSpecialtyName(String specialtyName) {
        assertTrue(ApiResponse.responseString.contains(specialtyName));
    }

    @When("I try to DELETE the last specialty returned by the API")
    public void iTryToDELETETheLastSpecialtyReturnedByTheAPI() {
        Specialty[] specialties=gson.fromJson(ApiResponse.responseString, Specialty[].class);
        ApiResponse.deletedSpecialty=specialties[specialties.length-1];
        specialtyStepImpl.deleteSpecialtyById(ApiResponse.deletedSpecialty.getId());
    }

    @Then("the specialties returned from the API don't contain the {}")
    public void theSpecialtiesReturnedFromTheAPIDonTContainTheDeletedSpecialty(String type) {
        specialtyStepImpl.getSpecialties();
        assertFalse(ApiResponse.responseString.contains(gson.toJson(ApiResponse.deletedSpecialty,Specialty.class)));
    }


}

