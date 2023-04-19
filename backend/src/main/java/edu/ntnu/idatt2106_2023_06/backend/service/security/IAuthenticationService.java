package edu.ntnu.idatt2106_2023_06.backend.service.security;

import edu.ntnu.idatt2106_2023_06.backend.dto.security.AuthenticationResponseDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoginDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserRegisterDTO;

public interface IAuthenticationService {
    AuthenticationResponseDTO register(UserRegisterDTO userRegisterDTO);
    AuthenticationResponseDTO authenticate(UserLoginDTO userLoginDTO);

}