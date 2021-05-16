Feature: Cancelling user subscription
  In order to change the status of my membership in Bargain Service
  As a Customer
  I want to cancel my current subscription

  Background: There is an already registered user
    Given John is an already registered customer with email address john@john.com and password password
    And John is logged in

  Scenario Outline: : Cancelling user subscription
    Given John has an active <subscription> subscription
    When John cancels their subscription
    Then John shouldn't have any active subscription plan
    Examples:
      |  subscription   |
      |  silver         |
      |  gold           |
      |  platinum       |
