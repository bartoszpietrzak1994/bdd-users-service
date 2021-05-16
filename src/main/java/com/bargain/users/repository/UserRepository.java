package com.bargain.users.repository;

import com.bargain.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByRef(String ref);

    User findByEmail(String email);
}
