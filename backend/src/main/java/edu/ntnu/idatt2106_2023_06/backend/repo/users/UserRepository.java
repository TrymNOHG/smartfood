package edu.ntnu.idatt2106_2023_06.backend.repo.users;

import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     Retrieves an Optional User instance based on the provided username.
     @param username the username to search for
     @return an Optional User instance
     */
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);


}