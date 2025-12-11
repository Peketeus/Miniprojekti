Feature: Add a new reference
    As a user
    I want to add a reference

    Scenario: Add a reference with valid data
        Given the reference list is empty
        When I add a new reference with type "Book" and key "BK00" and title "Kalevala"
        Then the reference list should contain reference with key "BK00"

    Scenario: Search reference by type
        Given multiple references exits with various types
        When I search for references with type "Book"
        Then I should only see references with type "Book"
        And I should not see references with other types