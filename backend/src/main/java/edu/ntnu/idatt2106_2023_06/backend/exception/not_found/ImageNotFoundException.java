package edu.ntnu.idatt2106_2023_06.backend.exception.not_found;

public class ImageNotFoundException extends NotFoundException {

    public ImageNotFoundException(String imageName) {
        super("Image", imageName);
    }

    public ImageNotFoundException(Long imageId) {
        super("Image", imageId);
    }
}
