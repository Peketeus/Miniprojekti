Feature: Add a new reference
    As a user
    I want to add a reference

    Scenario: Add a reference with valid data
        Given the reference list is empty
        When I add a new reference with type "Book" and key "BK00" and title "Kalevala"
        Then the reference list should contain reference with key "BK00"