Feature: Izertis Website

  @smoke
  Scenario: Visit Izertis home page and verify title
    Given I navigate to "https://www.izertis.com"
    Then I should see the page title as "Izertis. Passion for Technology - Izertis"
