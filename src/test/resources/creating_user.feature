Feature: Being a user in Bargain Service
  In order to be aware of the best deals
  As a Customer
  I want to be able to create an account in Bargain Service

  Scenario: Creating a user in Bargain Service
    Given there is a customer John with email address bartosz.pietrzak@gmail.com
    And John's password is well-protected-password
    When John signs up
    Then John's account should be created
    Then John shouldn't have any active subscription plan
