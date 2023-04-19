package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class CategoryNotFound extends NotFoundException {

    public CategoryNotFound(String categoryName) {
        super("Category", categoryName);
    }
    public CategoryNotFound(Long categoryId) {
        super("Category", categoryId);
    }

}
