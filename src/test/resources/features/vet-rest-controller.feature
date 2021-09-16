Feature: Test cases for vet endpoint

  @GET
  Scenario Outline: Verify GET all vets
    Given I have at least one vet returned from the API
    When I request all vets from the API
    Then all the vets returned are also in the DB
    And the status code is <statusCode>.
    Examples:
      | statusCode |
      | 200        |

  @POST @createVet
  Scenario Outline: verify POST new vet (without specialty)
    Given The PetClinic application is up and running
    When I create a new vet with the firstname <firstName> and lastname <lastName>
    And the status code is <statusCode>.
    When I request all vets from the API
    Then the vets returned from the API contain the createdVet
    And the created vet is also in the DB
    Examples:
      | firstName  | lastName   | statusCode |
      | TODAY_TIME | TODAY_TIME |201         |

  @POST @createVet
  Scenario Outline: verify POST new vet (with existing specialty ID)
    Given The PetClinic application is up and running
    And I have at least one specialty returned from the API
    When I create a new vet with the first specialty returned by the API and firstname <firstName> and <lastName>
    And the status code is <statusCode>.
    When I request all vets from the API
    Then the vets returned from the API contain the createdVet
    And the created vet is also in the DB
    Examples:
      | firstName  | lastName   | statusCode |
      | TODAY_TIME | TODAY_TIME |201         |
 #Even if we send a <id,name> only the name is taken into consideration

  @POST @updateVet
  Scenario Outline: Verify POST update a valid vet (without specialty)
    Given I have at least one vet returned from the API
    When I try to update the first vet returned by the API in firstname <firstName> and <lastName> by POST
    And the status code is <statusCode>.
    And I request all vets from the API
    Then the vets returned from the API contain the createdVet
    And the edited vet is also in the DB
    Examples:
      | firstName  | lastName   | statusCode |
      | TODAY_TIME | TODAY_TIME |201         |

  @POST @updateVet
  Scenario Outline: Verify POST update a valid vet (with existing specialty)
    Given I have at least one specialty returned from the API
    And I have at least one vet returned from the API
    When I try to update the first vet returned by the API in with the first specialty returned by the API and firstname <firstName> and <lastName> by POST
    And the status code is <statusCode>.
    And I request all vets from the API
    Then the vets returned from the API contain the createdVet
    And the edited vet is also in the DB
    Examples:
      | firstName  | lastName   | statusCode |
      | TODAY_TIME | TODAY_TIME |201         |


  @GET
  Scenario Outline: Verify GET a specific vet
    Given I have at least one vet returned from the API
    When I request the first vet returned by the API
    Then the response contains a vet
    And the requested vet is also in the DB
    And the status code is <statusCode>.
    Examples:
      | statusCode |
      | 200        |

  @PUT @updateVet
  Scenario Outline: Verify PUT a valid vet (without specialty)
    Given I have at least one vet returned from the API
    When I try to update the first vet returned by the API in firstname <firstName> and <lastName> by PUT
    And the status code is <statusCode>.
    And I request all vets from the API
    Then the vets returned from the API contain the editedVet
    And the edited vet is also in the DB
    Examples:
      | firstName  | lastName   | statusCode |
      | TODAY_TIME | TODAY_TIME | 204        |

  @PUT @updateVet
  Scenario Outline: Verify PUT a valid vet (with existing specialty)
    Given I have at least one vet returned from the API
    And I have at least one specialty returned from the API
    When I try to update the first vet returned by the API in with the first specialty returned by the API and firstname <firstName> and <lastName> by PUT
    And the status code is <statusCode>.
    And I request all vets from the API
    Then the vets returned from the API contain the editedVet
    And the edited vet is also in the DB
    Examples:
      | firstName  | lastName   | statusCode |
      | TODAY_TIME | TODAY_TIME | 204        |


  @DELETE @deleteVet
  Scenario Outline: Verify DELETE an existing vet
    Given I have at least one vet returned from the API
    When I try to DELETE the last vet returned by the API
    And the status code is <statusCode>.
    And I request all vets from the API
    Then the vets returned from the API don't contain the deletedVet
    And the deleted vet is not in the DB
    Examples:
      | statusCode |
      | 204        |

#      COMPLETED POSITIVE TESTS