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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    /**
     * Store a file on the server. The file will be stored in a directory with the name of the listingID. The file
     * must have the correct extension and name. A file with the same name will be overwritten and might break the
     * listing. The filename has to be the same as the total number of files in the directory.
     *
     * @param file The file to store
     * @param id The id of the listing
     * @param filename The name of the file
     * @return The filename without the extension
     */
    public String handleFileUpload(MultipartFile file, String id, String filename) {
        String originalFileName = file.getOriginalFilename();
        String extension = "";

        // Extract the file extension from the original file name
        if (originalFileName != null && originalFileName.lastIndexOf(".") > 0) {
            extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        }

        // Check if the file type is supported
        if (!extension.matches("(?i)(jpg|jpeg|png|gif|bmp)")) {
            throw new RuntimeException("File type not supported: " + extension);
        }

        // Construct the target location for the uploaded file
        Path targetLocation = Paths.get(fileStorageLocation + "/" + id + "/" + filename);

        try {
            // Create the target directory if it doesn't exist
            Files.createDirectories(targetLocation.getParent());

            // Copy the file contents to the target location
            Files.copy(file.getInputStream(), targetLocation);

            // Return the filename without the extension
            return filename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + originalFileName + " New filename: " + filename, e);
        }
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
    public MultipartFile getProfilePicture(String userID) throws IOException {
        return null;
    }
}