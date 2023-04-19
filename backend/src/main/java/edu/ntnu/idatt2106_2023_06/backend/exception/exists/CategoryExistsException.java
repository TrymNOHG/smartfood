package edu.ntnu.idatt2106_2023_06.backend.exception.exists;

public class CategoryExistsException extends ExistsException{

    public CategoryExistsException(String categoryName) {
        super("Category", categoryName);
    }

}
