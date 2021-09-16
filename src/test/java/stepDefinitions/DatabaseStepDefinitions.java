package stepDefinitions;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import models.ApiResponse;
import models.PetType;
import models.Specialty;
import models.Vet;
import net.thucydides.core.annotations.Steps;
import stepImplementation.DatabaseStepImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class DatabaseStepDefinitions {


  @Steps
  DatabaseStepImpl databaseStepImpl;


  @Given("^the specialities (.*) are deleted from DB$")
  public void deleteSpecialities (String specialities) {

    databaseStepImpl.deleteSpecialities(Arrays.asList(specialities.split(",")));

  }

  @And("the {} specialty is also in the DB")
  public void theEditedSpecialtyIsAlsoInTheDB(String type) {
    List<Specialty> dbResponse = databaseStepImpl.getAllSpecialities();
    boolean found=false;
    if(ApiResponse.createdSpecialty.getId()!=null){
      for(Specialty element:dbResponse){
        if(element.equals(ApiResponse.createdSpecialty)){
          found=true;
          break;
        }
      }
    }else{
      for(Specialty element:dbResponse){
        if(element.getName().equals(ApiResponse.createdSpecialty.getName())){
          found=true;
          break;
        }
      }
    }
    assertTrue(found);
  }

  @And("the {} specialty is not in the DB")
  public void theEditedSpecialtyIsNotInTheDB(String type) {
    List<Specialty> dbResponse = databaseStepImpl.getAllSpecialities();
    boolean found=false;
      for(Specialty element:dbResponse){
        if(element.equals(ApiResponse.deletedSpecialty)){
          found=true;
          break;
        }
      }
    assertFalse(found);
  }

  @And("the {} petType is also in the DB")
  public void theCreatedPetTypeIsAlsoInTheDB(String type) {
    List<PetType> dbResponse = databaseStepImpl.getAllPetTypes();
    boolean found=false;
    if(ApiResponse.createdPetType.getId()!=null){
      for(PetType element:dbResponse){
        if(element.equals(ApiResponse.createdPetType)){
          found=true;
          break;
        }
      }
    }else{
      for(PetType element:dbResponse){
        if(element.getName().equals(ApiResponse.createdPetType.getName())){
          found=true;
          break;
        }
      }
    }
    assertTrue(found);
  }

  @And("the {} petType is not in the DB")
  public void theDeletedPetTypeIsNotInTheDB(String type) {
    List<PetType> dbResponse = databaseStepImpl.getAllPetTypes();
    boolean found=false;
    for(PetType element:dbResponse){
      if(element.equals(ApiResponse.deletedPetType)){
        found=true;
        break;
      }
    }
    assertFalse(found);
  }


  @And("the {} vet is also in the DB")
  public void theCreatedVetIsAlsoInTheDB(String type) {
    List<Vet> dbResponse = databaseStepImpl.getAllVets();
    boolean found=false;
    if(ApiResponse.createdVet.getId()!=null){
      for(Vet element:dbResponse){
        if(element.equals(ApiResponse.createdVet)){
          found=true;
          break;
        }
      }
    }else{
      for(Vet vet:dbResponse){
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
    }
    assertTrue(found);
  }

  @And("the {} vet is not in the DB")
  public void theDeletedVetIsNotInTheDB(String type) {
    List<Vet> dbResponse = databaseStepImpl.getAllVets();
    boolean found=false;
    for(Vet element:dbResponse){
      if(element.equals(ApiResponse.deletedVet)){
        found=true;
        break;
      }
    }
    assertFalse(found);
  }
}
