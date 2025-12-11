Feature: Search existing references
  As a user
  I can search existing references

  Scenario: Search references by tag
    Given multiple references exists with various tags
    When I search for references with tag "important"
    Then I should only see references tagged with "important"
    And  I should not see references with other tags

  Scenario: Search reference by type
    Given multiple references exits with various types
    When I search for references with type "Book"
    Then I should only see references with type "Book"
    And I should not see references with other types

  Scenario: Search references by data
    Given multiple references with different data exists
    When I search for references that has author "John Smith"
    Then I should only see references with author "John Smith"
    And I should not see references with other authors