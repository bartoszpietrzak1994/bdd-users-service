package com.bargain.users.steps;

import com.bargain.users.SpringTest;
import com.bargain.users.repository.UserRepository;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class BeforeScenario extends SpringTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void beforeScenario() {
        userRepository.deleteAll();
        sharedStorage.clear();
    }
}
