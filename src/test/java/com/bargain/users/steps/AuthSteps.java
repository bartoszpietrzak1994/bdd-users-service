package com.bargain.users.steps;

import com.bargain.users.SpringTest;
import com.bargain.users.client.AuthController;
import com.bargain.users.client.dto.UserDto;
import com.bargain.users.client.dto.request.LoginRequestDto;
import com.bargain.users.client.dto.response.RegisterResponseDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AuthSteps extends SpringTest {

    private static final String USER_STORAGE_KEY_FORMAT = "%s_%s";

    @Given("^there is a customer (.*) with email address (.*)$")
    public void there_is_a_customer_with_email_address(String name, String email) {
        UserDto user = UserDto.builder().email(email).build();
        sharedStorage.set(String.format(USER_STORAGE_KEY_FORMAT, "user", name), user);
    }

    @Given("^(.*)'s password is (.*)$")
    public void customer_password_is(String name, String password) {
        UserDto user = (UserDto) sharedStorage.get(String.format(USER_STORAGE_KEY_FORMAT, "user", name));
        user.setPassword(password);
        sharedStorage.set(String.format(USER_STORAGE_KEY_FORMAT, "user", name), user);
    }

    @Given("^(.*) is an already registered customer with email address (.*) and password (.*)$")
    public void is_already_registered_customer_with_email_address_and_password(String name, String email, String password) {
        UserDto user = UserDto.builder().email(email).password(password).build();
        registerUser(user);
        user.setRef((String) sharedStorage.get("user_reference"));
        sharedStorage.set(String.format("%s_%s", "user", name), user);
    }

    @Given("^(.*) is logged in$")
    public void user_is_already_logged_in(String name) {
        UserDto user = (UserDto) sharedStorage.get(String.format("%s_%s", "user", name));
        authenticateUser(user);
    }

    @When("^(.*) signs up$")
    public void customer_signs_up(String name) {
        UserDto user = (UserDto) sharedStorage.get(String.format(USER_STORAGE_KEY_FORMAT, "user", name));
        registerUser(user);
        user.setRef((String) sharedStorage.get("user_reference"));
        sharedStorage.set(String.format(USER_STORAGE_KEY_FORMAT, "user", name), user);
    }

    @When("^(.*) tries to log in using email (.*) and password (.*)$")
    public void try_to_log_in_using_email_and_password(String name, String email, String password) {
        UserDto user = UserDto.builder().email(email).password(password).build();
        authenticateUser(user);
    }

    @Then("^(.*)'s account should be created$")
    public void account_should_be_created(String name) {
        UserDto user = (UserDto) sharedStorage.get(String.format(USER_STORAGE_KEY_FORMAT, "user", name));
        authenticateUser(user);
        should_be_successful();
    }

    @Then("it should be successful")
    public void should_be_successful() {
        assertThat((boolean) sharedStorage.get("response_success")).isTrue();
    }

    @Then("it should not be successful")
    public void should_not_be_successful() {
        assertThat((boolean) sharedStorage.get("response_success")).isFalse();
    }

    private void registerUser(UserDto userDto) {
        int status;
        RegisterResponseDto responseDto;
        try {
            MockHttpServletResponse response = this.mockMvc
                    .perform(post(AuthController.PATH + "/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userDto))
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            status = response.getStatus();
            handleResponseStatus(status);
            responseDto = objectMapper.readValue(response.getContentAsString(), RegisterResponseDto.class);
        } catch (Exception e) {
            sharedStorage.set("response_success", false);
            return;
        }

        sharedStorage.set("user_reference", responseDto.getRef());
    }

    private void authenticateUser(UserDto userDto) {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();

        int status;
        try {
            status = this.mockMvc
                    .perform(post(AuthController.PATH + "/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequestDto))
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getStatus();

            handleResponseStatus(status);
        } catch (Exception e) {
            sharedStorage.set("response_success", false);
        }
    }

    private void handleResponseStatus(int status) throws Exception {
        if (status >= 200 && status < 300) {
            sharedStorage.set("response_success", true);
            return;
        }

        throw new Exception("Unexpected http code");
    }
}
