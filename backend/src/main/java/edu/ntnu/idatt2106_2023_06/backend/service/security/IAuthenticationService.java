package edu.ntnu.idatt2106_2023_06.backend.service.security;

import edu.ntnu.idatt2106_2023_06.backend.dto.security.AuthenticationRequestDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.security.AuthenticationResponseDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserCreateDTO;

public interface IAuthenticationService {
    AuthenticationResponseDTO register(UserCreateDTO userCreateDTO);
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);

}