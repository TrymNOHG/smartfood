package edu.ntnu.idatt2106_2023_06.backend.service.files;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface IFileStorageService {

    void storeProfilePicture(String userID, MultipartFile profilePicture) throws IOException;

    void deleteProfilePicture() throws IOException;

    byte[] getProfilePicture(long userId);

}