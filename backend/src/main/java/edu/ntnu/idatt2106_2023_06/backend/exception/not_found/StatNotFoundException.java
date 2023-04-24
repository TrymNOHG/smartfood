package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

import edu.ntnu.idatt2106_2023_06.backend.repo.stat.StatTypeRepository;

public class StatNotFoundException extends NotFoundException {

    public StatNotFoundException(Long id) {
        super("Stat", id);
    }

    public StatNotFoundException(String name) {
        super("Stat", name);
    }
}
