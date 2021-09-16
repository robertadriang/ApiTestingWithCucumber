package util.database;


import lombok.Getter;


@Getter
public enum DbTables {
  SPECIALITIES("specialties"),PET_TYPES("types"),VETS("vets"),VET_SPECIALTIES("vet_specialties");

  private final String tableName;


  DbTables (String tableName) {

    this.tableName = tableName;
  }

}
