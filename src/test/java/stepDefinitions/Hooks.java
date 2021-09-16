package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import models.ApiResponse;
import net.thucydides.core.annotations.Steps;
import stepImplementation.DatabaseStepImpl;
import stepImplementation.PetTypeStepImpl;
import stepImplementation.SpecialtyStepImpl;
import stepImplementation.VetStepImpl;
import util.database.DbConnection;
import util.database.Queries;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Hooks {
    DbConnection database = new DbConnection();

    @Steps
    SpecialtyStepImpl specialtyStepImpl;

    @Steps
    PetTypeStepImpl petTypeStepImpl;

    @Steps
    VetStepImpl vetStepImpl;

    @Steps
    DatabaseStepImpl databaseStepImpl;

    @After("@updateSpecialty")
    public void updatePostSpeciality() {
        try (
                PreparedStatement preparedStatement = database.connectToDatabase().prepareStatement(
                        Queries.UPDATE_SPECIALITY)
        ) {
            preparedStatement.setInt(1, ApiResponse.initialSpecialty.getId());
            preparedStatement.setString(2,ApiResponse.initialSpecialty.getName());
            preparedStatement.setInt(3, ApiResponse.createdSpecialty.getId());
            preparedStatement.setString(4,ApiResponse.createdSpecialty.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: ");
        }
    }

    @After("@createSpecialty")
    public void deleteSpecialty() {
        try (
                PreparedStatement preparedStatement = database.connectToDatabase().prepareStatement(
                        Queries.DELETE_SPECIALITY_BY_NAME)
        ) {
            preparedStatement.setString(1,ApiResponse.createdSpecialty.getName());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Database error");
        }
    }

    @Before("@deleteSpecialty")
    public void createSpecialty(){
        specialtyStepImpl.createSpecialty(java.time.LocalDateTime.now().toString());
    }

    @After("@createPetType")
    public void deletePetType(){
        try (
                PreparedStatement preparedStatement = database.connectToDatabase().prepareStatement(
                        Queries.DELETE_PET_TYPE_BY_NAME)
        ) {
            preparedStatement.setString(1,ApiResponse.createdPetType.getName());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Database error");
        }
    }

    @After("@updatePetType")
    public void updatePetType() {
        try (
                PreparedStatement preparedStatement = database.connectToDatabase().prepareStatement(
                        Queries.UPDATE_PET_TYPE)
        ) {
            preparedStatement.setInt(1, ApiResponse.initialPetType.getId());
            preparedStatement.setString(2,ApiResponse.initialPetType.getName());
            preparedStatement.setInt(3, ApiResponse.createdPetType.getId());
            preparedStatement.setString(4,ApiResponse.createdPetType.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: ");
        }
    }

    @Before("@deletePetType")
    public void createPetType(){
        petTypeStepImpl.createPetType(java.time.LocalDateTime.now().toString());
    }

    @After("@createVet")
    public void deleteVet(){
        int id=databaseStepImpl.getVetId(ApiResponse.createdVet.getFirstName(),ApiResponse.createdVet.getLastName());
        try (
                PreparedStatement deleteRelation = database.connectToDatabase().prepareStatement(
                        Queries.DELETE_VET_SPECIALTY_BY_ID);
                PreparedStatement deleteVet = database.connectToDatabase().prepareStatement(
                        Queries.DELETE_VET_BY_ID)
        ) {
            deleteRelation.setInt(1,id);
            deleteVet.setInt(1,id);

            deleteRelation.executeUpdate();
            deleteVet.executeUpdate();

        }catch(SQLException e){
            System.out.println("Database error");
        }
    }

    @After("@updateVet")
    public void updateVet() {
        try (
                PreparedStatement preparedStatement = database.connectToDatabase().prepareStatement(
                        Queries.UPDATE_VET)
        ) {
            preparedStatement.setInt(1, ApiResponse.initialVet.getId());
            preparedStatement.setString(2,ApiResponse.initialVet.getFirstName());
            preparedStatement.setString(3,ApiResponse.initialVet.getLastName());
            preparedStatement.setInt(4, ApiResponse.createdVet.getId());
            preparedStatement.setString(5,ApiResponse.createdVet.getFirstName());
            preparedStatement.setString(6,ApiResponse.createdVet.getLastName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: ");
        }
    }

    @Before("@deleteVed")
    public void createVet(){
        String name=java.time.LocalDateTime.now().toString();
        vetStepImpl.createVet(name,name);
    }


}
