package stepDefinitions;

import com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.ApiResponse;
import models.Specialty;
import models.Vet;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.lang3.StringUtils;
import stepImplementation.DatabaseStepImpl;
import stepImplementation.SpecialtyStepImpl;
import stepImplementation.VetStepImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class VetStepDefinitions {
    @Steps
    VetStepImpl vetStepImpl;

    @Steps
    SpecialtyStepImpl specialtyStepImpl;


    Gson gson=new Gson();

    @Given("I have at least one vet returned from the API")
    public void iHaveAtLeastOneVetReturnedFromTheAPI() {
        vetStepImpl.getVets();
        assertTrue(StringUtils.isNotEmpty(ApiResponse.responseString));
    }

    @When("I request all vets from the API")
    public void iRequestAllVetsFromTheAPI() {
        vetStepImpl.getVets();
    }

    @When("I create a new vet with the firstname {} and lastname {}")
    public void createVetWithNames(String firstName,String lastName) {
        if(firstName.equals("TODAY_TIME")){
            firstName=java.time.LocalDateTime.now().toString();
        }
        if(lastName.equals("TODAY_TIME")){
            lastName=java.time.LocalDateTime.now().toString();
        }
        vetStepImpl.createVet(firstName,lastName);
    }

    @When("I create a new vet with the first specialty returned by the API and firstname {} and {}")
    public void createVetComplete(String firstName,String lastName) {
        if(firstName.equals("TODAY_TIME")){
            firstName=java.time.LocalDateTime.now().toString();
        }
        if(lastName.equals("TODAY_TIME")){
            lastName=java.time.LocalDateTime.now().toString();
        }

        specialtyStepImpl.getSpecialties();
        List<Specialty> specialties= new ArrayList<>();
        specialties.add(gson.fromJson(ApiResponse.responseString, Specialty[].class)[0]);
        vetStepImpl.createVet(firstName,lastName,specialties);
    }

    @Then("the vets returned from the API contain the {}")
    public void theVetsReturnedFromTheAPIContainTheCreatedVet(String type) {
        vetStepImpl.getVets();
        if(ApiResponse.createdVet.getId()!=null){
            assertTrue(ApiResponse.responseString.contains(gson.toJson(ApiResponse.createdVet, Vet.class)));
        }else{
            List<Vet> apiResponse = Arrays.asList(
                    gson.fromJson(ApiResponse.responseString, Vet[].class)
            );
            boolean found=false;
            for(Vet vet:apiResponse){
               if(
                        vet.getFirstName().equals(ApiResponse.createdVet.getFirstName())
                                &&
                                vet.getLastName().equals(ApiResponse.createdVet.getLastName())
                                &&
                        vet.getSpecialties().equals(ApiResponse.createdVet.getSpecialties())
               ){
                    found=true;
                    break;
                }
            }
            assertTrue(found);
        }
    }

    @When("I try to update the first vet returned by the API in firstname {} and {} by {}")
    public void iTryToUpdateTheFirstVetReturnedByTheAPIInFirstnameFirstNameAndLastNameByPOST(String firstName, String lastName,String method) {
        ApiResponse.initialVet= gson.fromJson(ApiResponse.responseString, Vet[].class)[0];
        ApiResponse.createdVet=gson.fromJson(ApiResponse.responseString, Vet[].class)[0];
        ApiResponse.createdVet.setFirstName(firstName);
        ApiResponse.createdVet.setLastName(lastName);

        vetStepImpl.updateVet(method);
    }



    @When("I request the first vet returned by the API")
    public void iRequestTheFirstVetReturnedByTheAPI() {
        vetStepImpl.getVets();
        int firstId=gson.fromJson(ApiResponse.responseString,Vet[].class)[0].getId();
        vetStepImpl.getVetById(firstId);
    }

    @Then("the response contains a vet")
    public void theResponseContainsAVet() {
        ApiResponse.initialVet= gson.fromJson(ApiResponse.responseString, Vet.class);
        ApiResponse.createdVet=ApiResponse.initialVet;
        assertNotNull(ApiResponse.initialVet); /// ApiResponse.initialSpecialty is of specialty type sspecialtyStepImpl = nullo we only need to check that it is not null

    }

    @When("I try to DELETE the last vet returned by the API")
    public void iTryToDELETETheLastVetReturnedByTheAPI() {
        Vet[] vets=gson.fromJson(ApiResponse.responseString, Vet[].class);
        ApiResponse.deletedVet=vets[vets.length-1];
        vetStepImpl.deleteVetById(ApiResponse.deletedVet.getId());
    }

    @Then("the vets returned from the API don't contain the deletedVet")
    public void theVetsReturnedFromTheAPIDonTContainTheDeletedVet() {
        vetStepImpl.getVets();
        assertFalse(ApiResponse.responseString.contains(gson.toJson(ApiResponse.deletedVet,Vet.class)));
    }

    @When("I try to update the first vet returned by the API in with the first specialty returned by the API and firstname {} and {} by {}")
    public void updateVetWithSpecialty(String firstName,String lastName,String method) {
        ApiResponse.initialVet= gson.fromJson(ApiResponse.responseString, Vet[].class)[0];
        ApiResponse.createdVet=gson.fromJson(ApiResponse.responseString, Vet[].class)[0];

        specialtyStepImpl.getSpecialties();
        List<Specialty> specialties=new ArrayList<>();
        specialties.add(gson.fromJson(ApiResponse.responseString, Specialty[].class)[0]);

        if(firstName.equals("TODAY_TIME")){
            firstName=java.time.LocalDateTime.now().toString();
        }
        if(lastName.equals("TODAY_TIME")){
            lastName=java.time.LocalDateTime.now().toString();
        }

        ApiResponse.createdVet.setFirstName(firstName);
        ApiResponse.createdVet.setLastName(lastName);
        ApiResponse.createdVet.setSpecialties(specialties);

        vetStepImpl.updateVet(method);

    }
}
