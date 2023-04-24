package edu.ntnu.idatt2106_2023_06.backend.repo.users;

import edu.ntnu.idatt2106_2023_06.backend.model.users.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This repository provides CRUD operations for the Token entity.
 * It extends JpaRepository and JpaSpecificationExecutor interfaces.
 * JpaRepository provides basic CRUD operations while JpaSpecificationExecutor provides
 * search functionality using specifications.
 *
 * @author Trym Hamer Gudvangen
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    /**
     * This method retrieves the token attached to the given id.
     * @param id    ID of the token, given as a Long object.
     * @return      Token object
     */
    Optional<Token> findTokenByTokenId(Long id);

    /**
     * This method retrieves the token attached to the given user id.
     * @param userId    ID of the user, given as a Long object.
     * @return          Token object
     */
    Optional<Token> findTokenByUser_userId(Long userId);

}
