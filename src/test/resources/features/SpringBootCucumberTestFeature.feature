Feature: Rest API functionality
  Scenario: User is able to register and login
    Given That an user is able to register
    When I login to my account
    Then JWT key is returned

  Scenario: User is able to add, update, and delete expense entry
    Given A list of expenses are available
    When I search for one expense by id
    Then The expense is displayed
    When I search for expenses by user
    Then A list of expenses is displayed
    When I add an expense to my expense list
    Then the expense is added
    When I update an expense from my list of expenses
    Then The expense is updated





