Feature: I open the store and choose a jacket

  Background:
    Given I open the Store Website

  @WebTest
  Scenario: I choose the costliest Jacket
    Given I choose the mens jacket section
    When I pick the cheapest jacket
    And I choose Jacket options
      | Color | Orange |
      | Size  | M      |
    And I verify jacket details
    And I Add the jacket to the cart
    Then I see success message on page
    Then I quit
