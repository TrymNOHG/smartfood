package edu.ntnu.idatt2106_2023_06.backend.service.files;

import edu.ntnu.idatt2106_2023_06.backend.exception.illegal.IllegalFileTypeException;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.ImageNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * Service for handling file uploads. The service will store the files in the directory src/main/resources/images.
 *
 * @author Brage H. Kvamme
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class FileStorageService implements IFileStorageService {

    private Path fileStorageLocation;
    private final JwtService jwtService;
    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);


    /**
     * Constructor for the FileStorageService. The file storage location is set to src/main/resources/images.
     */
    public FileStorageService() throws IOException {
        this.jwtService = new JwtService();
        this.fileStorageLocation = Paths.get("src/main/resources/images").toAbsolutePath().normalize();
        Files.createDirectories(this.fileStorageLocation);
    }

    /**
     * Sets the file storage location. This method is used for testing purposes.
     *
     * @param fileStorageLocation The file storage location.
     */
    public void setFileStorageLocation(Path fileStorageLocation) {
        this.fileStorageLocation = fileStorageLocation;
    }

    @Override
    public void storeProfilePicture(String userId, MultipartFile profilePicture) throws IOException {
        if (profilePicture != null && !profilePicture.isEmpty()) {
            String contentType = profilePicture.getContentType();
            if (contentType != null && contentType.startsWith("image/") ) {
                Path targetLocation = fileStorageLocation.resolve(userId);
                Files.copy(profilePicture.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            } else {
                throw new IllegalFileTypeException("Profile picture must be an image.");
            }
        }
    }


    @Override
    public void deleteProfilePicture() throws IOException {
        String userId = Long.toString(jwtService.getAuthenticatedUserId());
        Path targetLocation = fileStorageLocation.resolve(userId);
        try{
            if(Files.deleteIfExists(targetLocation))
                logger.info("Profile picture deleted for user " + userId);
            else {
                logger.info("No profile picture found for user " + userId);
                throw new NoSuchFileException("No profile picture found for user with ID " + userId);
            }
        } catch (NoSuchFileException e) {
            logger.info("No profile picture found for user " + userId);
            throw new NoSuchFileException("No profile picture found for user with ID " + userId);
        }
    }

    @Override
    public byte[] getProfilePicture(long imageId) {
        Path filePath = this.fileStorageLocation.resolve(Long.toString(imageId)).normalize();
        File file = filePath.toFile();
        if (!file.exists()) {
            return null;
        }
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new ImageNotFoundException("Could not find image with ID " + imageId);
        }
    }
}