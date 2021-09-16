
Feature: Test cases for owner endpoint

  Test scripts for owner endpoint.

  Background:
    Given The PetClinic application is up and running

  Scenario: Verify the list of owners for a user
    When The user get the list with all owners
    Then A list of owners is returned

  Scenario: Verify that user can create a new owner
    When The user creates owner with firstName Cristina and lastName Pichiu
    Then The Cristina Pichiu owner is in the response

  Scenario Outline: Verify that user can create a new owner 2
    When The user creates owner with firstName <firstName> and lastName <lastName>
    Then The <firstName> <lastName> owner is in the response
    And The <lastName> owner is created

    Examples:
      | firstName | lastName |
      | Cristina  | Pichiu   |
#      | Andreea   | Popa     |

  Scenario Outline: Verify that user can create a new owner - second example
    When The user creates a new owner with firstName <firstName> and lastName <lastName> second example
    Then The new <lastName> owner is created - second example

    Examples:
      | firstName | lastName |
      | Simona    | Pavel    |

  Scenario: Verify that user can create a new owner - 3
    When The user creates a new owner
      | address      | city | firstName | lastName | telephone  | petName |
      | test address | Iasi | Laura     | Popescu  | 0763123456 | Felix   |
    Then The Popescu owner is created


#  Tema: de creat cate un scenariu pentru a testa: GET /api/pets si POST /api/pets (verificati colectia din swagger)
