package edu.ntnu.idatt2106_2023_06.backend.exception.exists;

/**
 *  An exception that is thrown when a category already exists in the database.
 *
 *  @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public class CategoryExistsException extends ExistsException{

    /**
     * This method constructs a {@code CategoryExistsException} with the specified category name.
     *
     * @param categoryName The name of the category that already exists, given as a String
     */
    public CategoryExistsException(String categoryName) {
        super("Category", categoryName);
    }

}
