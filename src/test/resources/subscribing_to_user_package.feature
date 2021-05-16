Feature: Subscribing to a user package
  In order to receive more detailed offers
  As a Customer
  I want to be able to subscribe to a user package

  Background: There is an already registered user
    Given John is an already registered customer with email address john@john.com and password password
    And John is logged in

  Scenario Outline: Being able to subscribe to a user package
    When John subscribes for the <subscription> subscription
    Then John should have an active <subscription> subscription
    Examples:
      |  subscription |
      |  bronze       |
      |  silver       |
      |  gold         |
      |  platinum     |
