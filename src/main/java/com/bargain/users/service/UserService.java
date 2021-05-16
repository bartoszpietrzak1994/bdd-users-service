package com.bargain.users.service;

import com.bargain.users.model.User;
import com.bargain.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.InvalidParameterException;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new EntityNotFoundException();
        }

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new InvalidParameterException();
        }
    }

    public String register(String email, String password) {
        String userRef = UUID.randomUUID().toString();

        String encodedPassword = bCryptPasswordEncoder.encode(password);

        User user = User.builder()
                .email(email)
                .ref(userRef)
                .password(encodedPassword)
                .build();

        userRepository.save(user);

        return userRef;
    }

    public User getByRef(String ref) {
        return userRepository.findByRef(ref);
    }
}
