Feature: Rest API functionality

  Scenario: User is able to add, update, and delete expense entry
    Given A list of expenses are available
    When I search for one expense by id
    Then expense is displayed
