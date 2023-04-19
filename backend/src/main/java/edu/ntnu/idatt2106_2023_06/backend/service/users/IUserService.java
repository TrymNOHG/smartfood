package edu.ntnu.idatt2106_2023_06.backend.service.users;

import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserDeletionDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserPasswordUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserUpdateDTO;

public interface IUserService {
    void updateUser(UserUpdateDTO userUpdateDTO, byte[] profilePicture);
    void updateUserPassword(UserPasswordUpdateDTO userPasswordUpdateDTO, String username);
    void deleteUser(UserDeletionDTO userDeletionDTO);

    UserLoadDTO loadUserDTOByUsername(String username);
}
