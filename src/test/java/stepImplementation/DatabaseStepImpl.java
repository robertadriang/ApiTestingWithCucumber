package stepImplementation;

import lombok.extern.slf4j.Slf4j;
import models.ManyToMany;
import models.PetType;
import models.Specialty;
import models.Vet;
import util.database.DbConnection;
import util.database.DbTables;
import util.database.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class DatabaseStepImpl {

  DbConnection database = new DbConnection();


  public List<Specialty> getAllSpecialities () {

    String table = DbTables.SPECIALITIES.getTableName();

    List<Specialty> specialities = new ArrayList<>();

    try (
        PreparedStatement preparedStatement = database.connectToDatabase().prepareStatement(
            Queries.SELECT_ALL_FROM_TABLE+table)
    ) {

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {

        Specialty speciality = Specialty.builder()
            .id(resultSet.getInt("id"))
            .name(resultSet.getString("name"))
            .build();

        specialities.add(speciality);
      }
    } catch (SQLException e) {
      log.error("Database error: ", e);
    }

    return specialities;
  }


  public void deleteSpecialities (List<String> specialities) {

    try (
        PreparedStatement preparedStatement = database.connectToDatabase().prepareStatement(
            Queries.DELETE_SPECIALTY_BY_ID_AND_NAME)
    ) {

      preparedStatement.setString(1, specialities.get(0));
      preparedStatement.setString(2, specialities.get(1));

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      log.error("Database error: ", e);
    }

  }

    public List<PetType> getAllPetTypes() {
      String table = DbTables.PET_TYPES.getTableName();
      List<PetType> petTypes = new ArrayList<>();

      try (
              PreparedStatement preparedStatement = database.connectToDatabase().prepareStatement(
                      Queries.SELECT_ALL_FROM_TABLE+table)
      ) {

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

          PetType petType = PetType.builder()
                  .id(resultSet.getInt("id"))
                  .name(resultSet.getString("name"))
                  .build();

          petTypes.add(petType);
        }
      } catch (SQLException e) {
        log.error("Database error: ", e);
      }

      return petTypes;
    }

    // Get all vets by taking all the vets from the vets table and assigning to each of them the specialities associated based on the vet_specialities table
  public List<Vet> getAllVets() {

    String vetsTableName = DbTables.VETS.getTableName();
    String relationTableName = DbTables.VET_SPECIALTIES.getTableName();

    List<Vet> vets = new ArrayList<>();
    List<ManyToMany> relations=new ArrayList<>();

    try (
            PreparedStatement vetStatement = database.connectToDatabase().prepareStatement(
                    Queries.SELECT_ALL_FROM_TABLE+vetsTableName);
            PreparedStatement relationStatement = database.connectToDatabase().prepareStatement(
            Queries.SELECT_ALL_FROM_TABLE+relationTableName)
    ) {

      ResultSet resultSet = vetStatement.executeQuery();
      while (resultSet.next()) {
        Vet vet = Vet.builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .specialties(new ArrayList<>())
                .build();
        vets.add(vet);
      }// Build the list of vets

      resultSet=relationStatement.executeQuery();
      while (resultSet.next()) {
        ManyToMany relation = ManyToMany.builder()
                .id1(resultSet.getInt("vet_id"))
                .id2(resultSet.getInt("specialty_id"))
                .build();

        relations.add(relation);
      }//Get all the relations between vets and specialties

      for(ManyToMany relation:relations){
        for(Vet vet:vets){
          if(vet.getId().equals(relation.getId1())){
            vet.specialties.add(getSpecialtyById(relation.getId2()));
          }
        }
      }///For each relation add the specialty to the vet.

    } catch (SQLException e) {
      log.error("Database error: ", e);
    }
    return vets;
  }

  public Specialty getSpecialtyById(int id){
    List<Specialty> specialties=new ArrayList<>();
    try (
            PreparedStatement preparedStatement = database.connectToDatabase().prepareStatement(
                    Queries.SELECT_SPECIALTY_BY_ID)
    ) {

      preparedStatement.setInt(1,id);
      ResultSet resultSet=preparedStatement.executeQuery();
      while (resultSet.next()) {
        Specialty speciality = Specialty.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
        specialties.add(speciality);
      }

    }catch (SQLException e){
      log.error("Database error: ",e);
    }
    return specialties.get(0);
  }

  public int getVetId(String firstName,String lastName){
    String relationTableName = DbTables.VET_SPECIALTIES.getTableName();
    List<Integer> ids = new ArrayList<>();

    try (
            PreparedStatement vetStatement = database.connectToDatabase().prepareStatement(
                    Queries.SELECT_VETID_BY_FIRSTNAME_LASTNAME)
    ){
      vetStatement.setString(1,firstName);
      vetStatement.setString(2,lastName);

      ResultSet resultSet=vetStatement.executeQuery();

      while (resultSet.next()) {
       ids.add(resultSet.getInt("id"));
      }//Get all the relations between vets and specialties

    }catch (SQLException e) {
      log.error("Database error: ", e);
    }
    return ids.get(ids.size()-1);
  }
}

