package edu.ntnu.idatt2106_2023_06.backend.repo.users;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     Retrieves an Optional User instance based on the provided username.
     @param username the username to search for
     @return an Optional User instance
     */
    Optional<User> findByUsername(String username);


}