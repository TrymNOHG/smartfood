package edu.ntnu.idatt2106_2023_06.backend.service.users;

import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserDeletionDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserPasswordUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserUpdateDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserService {

    @Transactional
    void updateUser(UserUpdateDTO userUpdateDTO, MultipartFile profilePicture, String username) throws IOException;

    void updateUserPassword(UserPasswordUpdateDTO userPasswordUpdateDTO, String username);
    void deleteUser(UserDeletionDTO userDeletionDTO);

    UserLoadDTO loadUserDTOByUsername(String username);
}
