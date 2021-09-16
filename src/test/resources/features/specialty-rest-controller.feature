Feature: Test cases for speciality endpoint

  @GET
  Scenario Outline: Verify GET all specialities
    Given I have at least one specialty returned from the API
    When I request all specialties from the API
    Then all the specialties returned are also in the DB
    And the status code is <statusCode>.
    Examples:
      | statusCode |
      | 200        |

  @POST @createSpecialty
  Scenario Outline: verify POST new specialty
    Given The PetClinic application is up and running
    When I create a new specialty with the name <specialtyName>
    And the status code is <statusCode>.
    When I request all specialties from the API
    Then the specialties returned from the API contain the createdSpecialty
    And the created specialty is also in the DB
    Examples:
      | specialtyName | statusCode |
      | TODAY_TIME    | 201        |


  @POST @updateSpecialty
  Scenario Outline: Verify POST update a valid speciality
    Given I have at least one specialty returned from the API
    When I try to update the first specialty returned by the API in <editedSpecialty> by POST
    And the status code is <statusCode>.
    And I request all specialties from the API
    Then the specialties returned from the API contain the editedSpecialty
    And the edited specialty is also in the DB
    Examples:
      | editedSpecialty | statusCode |
      | editedByPost    | 201        |

  @GET
  Scenario Outline: Verify GET a specific speciality
    Given I have at least one specialty returned from the API
    When I request the first specialty returned by the API
    Then the response contains a specialty
    And the requested specialty is also in the DB
    And the status code is <statusCode>.
    Examples:
      | statusCode |
      | 200        |

  @PUT @updateSpecialty
  Scenario Outline: Verify PUT a valid speciality
    Given I have at least one specialty returned from the API
    When I try to update the first specialty returned by the API in <editedSpecialty> by PUT
    And the status code is <statusCode>.
    And I request all specialties from the API
    Then the specialties returned from the API contain the editedSpecialty
    And the edited specialty is also in the DB
    Examples:
      | editedSpecialty | statusCode |
      | editedByPut     | 204        |

  @DELETE @deleteSpecialty
  Scenario Outline: Verify DELETE an existing speciality
    Given I have at least one specialty returned from the API
    When I try to DELETE the last specialty returned by the API
    And the status code is <statusCode>.
    And I request all specialties from the API
    Then the specialties returned from the API don't contain the deletedSpecialty
    And the deleted specialty is not in the DB
    Examples:
      | statusCode |
      | 204        |

#      COMPLETED POSITIVE TESTS