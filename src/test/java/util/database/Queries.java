package util.database;


public class Queries {

  public final static String SELECT_ALL_FROM_TABLE = "SELECT * FROM ";

  public final static String DELETE_SPECIALTY_BY_ID_AND_NAME = "DELETE FROM specialties where name in (?,?)";

  public final static String UPDATE_SPECIALITY="UPDATE specialties SET id=?,name=? WHERE id=? AND name=?";

  public final static String DELETE_SPECIALITY_BY_NAME="DELETE FROM specialties WHERE name=?";

  public final static String DELETE_PET_TYPE_BY_NAME="DELETE FROM types WHERE name=?";

  public final static String UPDATE_PET_TYPE="UPDATE types SET id=?,name=? WHERE id=? AND name=?";

  public final static String SELECT_SPECIALTY_BY_ID="SELECT * FROM specialties WHERE id=?";

  public final static String SELECT_VETID_BY_FIRSTNAME_LASTNAME="SELECT id FROM vets WHERE first_name=? and last_name=?";

  public final static String DELETE_VET_SPECIALTY_BY_ID="DELETE FROM vet_specialties WHERE vet_id=?";

  public final static String DELETE_VET_BY_ID="DELETE FROM vets WHERE id=?";

  public final static String UPDATE_VET="UPDATE vets SET id=?,first_name=?,last_name=? WHERE id=? AND first_name=? AND last_name=?";
}
