package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

/**
 *  An exception thrown when an image is not found.
 *
 *  @author Brage Halvorsen Kvamme, Trym Hamer Gudvangen
 */
public class ImageNotFoundException extends NotFoundException {

    /**
     * This method constructs an ImageNotFoundException with the specified image name.
     *
     * @param imageName The name of the image that was not found, given as a String.
     */
    public ImageNotFoundException(String imageName) {
        super("Image", imageName);
    }

    /**
     * This method constructs an ImageNotFoundException with the specified image ID.
     *
     * @param imageId The ID of the image that was not found, given as a Long object.
     */
    public ImageNotFoundException(Long imageId) {
        super("Image", imageId);
    }
}
