Feature: I open the store and choose the jacket

  Background:
    Given I open the Store Website

  @WebTest
  Scenario: I choose the costliest Jacket
    Given I choose the mens jacket section
    When I pick the costliest jacket
    And Choose size as "XL"
    And Choose Color as "Blue"
    And I Add the jacket to the cart
    Then I see success message on page