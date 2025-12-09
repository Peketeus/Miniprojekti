Feature: Add a new reference
    As a user
    I want to add a reference

    Scenario: Add a reference with valid data
        Given I have an empty reference list
        When I add a new reference
        Then the reference list should contain it