@cart @bdd @cart-flow
Feature: Shopping Cart Status
  As a user of SauceDemo
  I want to see my cart status across different pages
  So that I can track the products I've added

  Background:
    Given I am on the SauceDemo homepage
    And I login with valid credentials

  @critical @all @smoke
  Scenario: Cart status shows correct count across pages
    Given I am on the products page
    When I add a random product to cart
    Then I should see the cart badge with count 1
    When I navigate to the added product details
    Then I should see the remove button
    And I should see the cart badge with count 1
    When I navigate to the cart page
    Then I should see the checkout button
    And I should see the cart badge with count 1
