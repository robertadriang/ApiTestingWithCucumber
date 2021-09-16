package stepDefinitions;


import com.google.gson.Gson;
import io.cucumber.java.en.Then;
import models.ApiResponse;
import models.PetType;
import models.Specialty;
import models.Vet;
import net.thucydides.core.annotations.Steps;
import stepImplementation.DatabaseStepImpl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class DataValidationStepDefinitions {

    @Steps
    DatabaseStepImpl databaseStepImpl;
    Gson gson=new Gson();

    @Then("all the specialties returned are also in the DB")
    public void allTheSpecialtiesReturnedAreAlsoInTheDB() {
        List<Specialty> apiResponse = Arrays.asList(
                gson.fromJson(ApiResponse.responseString, Specialty[].class)
        );
        List<Specialty> dbResponse = databaseStepImpl.getAllSpecialities();
        assertEquals(apiResponse, dbResponse);
    }

    @Then("all the pet types returned are also in the DB")
    public void allThePetTypesReturnedAreAlsoInTheDB() {
        List<PetType> apiResponse = Arrays.asList(
                gson.fromJson(ApiResponse.responseString, PetType[].class)
        );
        List<PetType> dbResponse = databaseStepImpl.getAllPetTypes();
        assertEquals(apiResponse,dbResponse);

    }

    @Then("all the vets returned are also in the DB")
    public void allTheVetsReturnedAreAlsoInTheDB() {
        List<Vet> apiResponse = Arrays.asList(
                gson.fromJson(ApiResponse.responseString, Vet[].class)
        );
        List<Vet> dbResponse = databaseStepImpl.getAllVets();

        /// Order of results might differ so we need to sort the responses.
        apiResponse.sort(Comparator.comparingInt(Vet::getId));
        dbResponse.sort(Comparator.comparingInt(Vet::getId));

        assertEquals(apiResponse,dbResponse);
    }
}
