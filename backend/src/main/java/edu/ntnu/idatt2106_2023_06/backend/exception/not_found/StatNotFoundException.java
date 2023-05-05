package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatTypeRepository;

/**
 *  The exception thrown when a requested Stat cannot be found in the system.
 */
public class StatNotFoundException extends NotFoundException {

    /**
     * This method constructs a new StatNotFoundException with the specified id value.
     * @param id The id value of the missing Stat.
     */
    public StatNotFoundException(Long id) {
        super("Stat", id);
    }

    /**
     * This method constructs a new StatNotFoundException with the specified name value.
     * @param name The name value of the missing Stat.
     */
    public StatNotFoundException(String name) {
        super("Stat", name);
    }
}
