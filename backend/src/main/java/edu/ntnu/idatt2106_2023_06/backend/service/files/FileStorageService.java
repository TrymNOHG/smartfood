package edu.ntnu.idatt2106_2023_06.backend.service.files;

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

    private final Path fileStorageLocation;
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

    @Override
    public void storeProfilePicture(String userId, MultipartFile profilePicture) throws IOException {
        if (profilePicture != null && !profilePicture.isEmpty()) {
            Path targetLocation = fileStorageLocation.resolve(userId);
            Files.copy(profilePicture.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Override
    public void deleteProfilePicture() throws IOException {
        String userId = Long.toString(jwtService.getAuthenticatedUserId());
        Path targetLocation = fileStorageLocation.resolve(userId);
        try{
            Files.deleteIfExists(targetLocation);
            logger.info("Profile picture deleted for user " + userId);
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
            throw new ImageNotFoundException(imageId);
        }
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new ImageNotFoundException(imageId);
        }
    }
}