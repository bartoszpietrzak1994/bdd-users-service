Feature: Logging into Bargain Service
  In order to be able to use Bargain Service
  As a Customer
  I want to login using my credentials

  Background: There is an already registered user
    Given John is an already registered customer with email address john@john.com and password password

  Scenario: Logging in successfully
    When John tries to log in using email john@john.com and password password
    Then it should be successful

  Scenario: Logging in using wrong email address
    When John tries to log in using email wrong@address.com and password password
    Then it should not be successful

  Scenario: Logging in using wrong password
    When John tries to log in using email john@john.com and password invalid
    Then it should not be successful
