Feature: Search existing references
  As a user
  I can search existing references

  Scenario: Search references by tag
    Given multiple references exists with various tags
    When I search for references with tag "important"
    Then I should only see references tagged with "important"
    And  I should not see references with other tags
