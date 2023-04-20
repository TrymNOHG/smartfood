package edu.ntnu.idatt2106_2023_06.backend.service.files;

import edu.ntnu.idatt2106_2023_06.backend.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Objects;

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
    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    /**
     * Constructor for the FileStorageService. The file storage location is set to src/main/resources/images.
     */
    public FileStorageService() throws IOException {
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
    public void deleteProfilePicture(String userId) throws IOException {
        Path targetLocation = fileStorageLocation.resolve(userId);
        try{
            Files.deleteIfExists(targetLocation);
        } catch (NoSuchFileException e) {
            logger.info("No profile picture found for user " + userId);
            throw new NoSuchFileException("No profile picture found for user with ID " + userId);
        }
    }

    @Override
    public Resource getProfilePicture(String userId) throws IOException {
        Path targetLocation = fileStorageLocation.resolve(userId);

        try {
            Resource resource = new UrlResource(targetLocation.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("No profile picture found for user with ID " + userId);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("No profile picture found for user with ID " + userId);
        }
    }
}