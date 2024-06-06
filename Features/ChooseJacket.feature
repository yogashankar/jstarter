Feature: I open the store and choose a jacket

  Background:
    Given I open the Store Website

  Scenario: I choose the costliest Jacket
    Given I choose the mens jacket section
    When I pick the cheapest jacket
    And Choose size as "XS"
    And Choose Color as "Orange"
    And I Add the jacket to the cart
    Then I see success message on page