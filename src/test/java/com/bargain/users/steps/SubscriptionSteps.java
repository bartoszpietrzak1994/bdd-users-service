package com.bargain.users.steps;

import com.bargain.users.SpringTest;
import com.bargain.users.client.SubscriptionController;
import com.bargain.users.client.dto.SubscriptionStatus;
import com.bargain.users.client.dto.UserDto;
import com.bargain.users.client.dto.request.SubscribeRequestDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class SubscriptionSteps extends SpringTest {

    @Given("^(.*) has an active (.*) subscription")
    public void has_active_subscription(String name, String subscription) {
        user_applies_for_subscription(name, subscription);
    }

    @When("^(.*) subscribes for the (.*) subscription$")
    public void user_applies_for_subscription(String name, String subscription) {
        UserDto user = (UserDto) sharedStorage.get(String.format("%s_%s", "user", name));
        SubscriptionStatus subscriptionStatus = SubscriptionStatus.of(subscription);
        subscribe(user.getRef(), subscriptionStatus);
        assertThat((boolean) sharedStorage.get("response_success")).isTrue();
    }

    @When("^(.*) cancels their subscription$")
    public void user_cancels_package_subscription(String name) {
        UserDto user = (UserDto) sharedStorage.get(String.format("%s_%s", "user", name));
        cancelSubscription(user.getRef());
        assertThat((boolean) sharedStorage.get("response_success")).isTrue();
    }

    @Then("^(.*) should have an active (.*) subscription$")
    public void should_have_active_subscription(String name, String subscriptionName) {
        UserDto user = (UserDto) sharedStorage.get(String.format("%s_%s", "user", name));
        getSubscriptionForUser(user);

        SubscriptionStatus subscription = (SubscriptionStatus) sharedStorage.get("subscription");
        assertThat(subscription).isEqualTo(SubscriptionStatus.of(subscriptionName));
    }

    @Then("^(.*) shouldn't have any active subscription plan")
    public void should_not_have_any_active_subscription_plan(String name) {
        UserDto user = (UserDto) sharedStorage.get(String.format("%s_%s", "user", name));
        getSubscriptionForUser(user);

        SubscriptionStatus subscription = (SubscriptionStatus) sharedStorage.get("subscription");
        assertThat(subscription).isNull();
    }

    private void subscribe(String userRef, SubscriptionStatus subscriptionStatus) {
        SubscribeRequestDto subscribeRequestDto = SubscribeRequestDto.builder()
                .userRef(userRef)
                .subscriptionStatus(subscriptionStatus)
                .build();

        try {
            int status = this.mockMvc
                    .perform(post(SubscriptionController.PATH + "/subscribe")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(subscribeRequestDto))
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getStatus();

            handleResponseStatus(status);
        } catch (Exception e) {
            sharedStorage.set("response_success", false);
        }
    }

    private void cancelSubscription(String userRef) {
        try {
            MockHttpServletResponse response = this.mockMvc
                    .perform(post(SubscriptionController.PATH + "/user/" + userRef + "/cancel")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            handleResponseStatus(response.getStatus());
        } catch (Exception e) {
            sharedStorage.set("response_success", false);
        }
    }

    private void getSubscriptionForUser(UserDto user) {
        String userRef = user.getRef();
        SubscriptionStatus subscriptionStatus;

        int status;
        try {
            MockHttpServletResponse response = this.mockMvc
                    .perform(get(SubscriptionController.PATH + "/user/" + userRef)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            status = response.getStatus();
            handleResponseStatus(status);

            subscriptionStatus = objectMapper.readValue(response.getContentAsString(), SubscriptionStatus.class);
        } catch (Exception e) {
            sharedStorage.set("response_success", false);
            return;
        }

        sharedStorage.set("subscription", subscriptionStatus);
    }

    private void handleResponseStatus(int status) throws Exception {
        if (status >= 200 && status < 300) {
            sharedStorage.set("response_success", true);
            return;
        }

        throw new Exception("Unexpected http code");
    }
}
