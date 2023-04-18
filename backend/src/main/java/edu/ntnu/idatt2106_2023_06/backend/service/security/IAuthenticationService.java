package edu.ntnu.idatt2106_2023_06.backend.service.security;

public interface IAuthenticationService {
    AuthenticationResponse register(UserCreateDTO userCreateDTO);
    AuthenticationResponse authenticate(AuthenticationRequest request);

}