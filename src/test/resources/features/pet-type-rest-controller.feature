Feature: Test cases for pet-type endpoint

  @GET
  Scenario Outline: Verify GET all pettypes
    Given I have at least one pet type returned from the API
    When I request all pet types from the API
    Then all the pet types returned are also in the DB
    And the status code is <statusCode>.
    Examples:
      | statusCode |
      | 200        |

  @POST @createPetType
  Scenario Outline: verify POST new pet type
    Given The PetClinic application is up and running
    When I create a new petType with the name <petTypeName>
    And the status code is <statusCode>.
    When I request all pet types from the API
    Then the pet types returned from the API contain the createdPetType
    And the created petType is also in the DB
    Examples:
      | petTypeName | statusCode |
      | TODAY_TIME    | 201        |


  @POST @updatePetType
  Scenario Outline: Verify POST update a valid petType
    Given I have at least one pet type returned from the API
    When I try to update the first petType returned by the API in <editedPetType> by POST
    And the status code is <statusCode>.
    And I request all pet types from the API
    Then the pet types returned from the API contain the editedPetType
    And the edited petType is also in the DB
    Examples:
      | editedPetType | statusCode |
      | editedByPost    | 201        |

  @GET
  Scenario Outline: Verify GET a specific petType
    Given I have at least one pet type returned from the API
    When I request the first petType returned by the API
    Then the response contains a petType
    And the requested petType is also in the DB
    And the status code is <statusCode>.
    Examples:
      | statusCode |
      | 200        |

  @PUT @updatePetType
  Scenario Outline: Verify PUT a valid petType
    Given I have at least one pet type returned from the API
    When I try to update the first petType returned by the API in <editedPetType> by PUT
    And the status code is <statusCode>.
    And I request all pet types from the API
    Then the pet types returned from the API contain the editedPetType
    And the edited petType is also in the DB
    Examples:
      | editedPetType | statusCode |
      | editedByPut     | 204        |

  @DELETE @deletePetType
  Scenario Outline: Verify DELETE an existing petType
    Given I have at least one pet type returned from the API
    When I try to DELETE the last petType returned by the API
    And the status code is <statusCode>.
    And I request all pet types from the API
    Then the pet types returned from the API don't contain the deletedPetType
    And the deleted petType is not in the DB
    Examples:
      | statusCode |
      | 204        |

#      COMPLETED POSITIVE TESTS