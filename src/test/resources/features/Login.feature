@login @bdd @login-flow
Feature: User Login and Navigation
  As a user of SauceDemo
  I want to login with my credentials
  So that I can access the products page

  @critical @all @smoke
  Scenario: Successful login with valid credentials
    Given I am on the SauceDemo homepage
    When I login with valid credentials
    Then I should be on the products page
