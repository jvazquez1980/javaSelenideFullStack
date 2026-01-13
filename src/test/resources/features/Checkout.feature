@checkout @bdd
Feature: Checkout Flow
  As a user of SauceDemo
  I want to complete a purchase
  So that I can buy products

  Background:
    Given I am on the SauceDemo homepage
    And I login with valid credentials

  @critical @all
  Scenario: Complete checkout process successfully
    Given I am on the products page
    When I add a random product to cart
    And I navigate to the cart
    Then the cart badge should match the product count
    When I proceed to checkout
    And I fill the checkout form with:
      | firstName | lastName  | zipCode |
      | Quality   | Assurance | 12345   |
    And I continue to the overview page
    Then I should see payment information as "SauceCard"
    And I should see shipping information as "Free"
    And the total calculation should be correct
    When I finish the checkout
    Then I should see the success message "Thank you for your order"
    And I should be on the complete page

  @normal @all
  Scenario Outline: Complete checkout with different user data
    Given I am on the products page
    When I add a random product to cart
    And I proceed to checkout
    And I fill the checkout form with:
      | firstName   | lastName   | zipCode   |
      | <firstName> | <lastName> | <zipCode> |
    And I continue to the overview page
    Then I should see payment information as "SauceCard"
    When I finish the checkout
    Then I should see the success message "Thank you for your order"

    Examples:
      | firstName | lastName | zipCode |
      | John      | Doe      | 10001   |
      | Jane      | Smith    | 90210   |
      | Bob       | Johnson  | 60601   |
