package edu.ntnu.idatt2106_2023_06.backend.controller;

import edu.ntnu.idatt2106_2023_06.backend.dto.security.AuthenticationResponseDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserLoginDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserRegisterDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserPasswordUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.UserUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.service.files.FileStorageService;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.AuthenticationService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import edu.ntnu.idatt2106_2023_06.backend.service.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

    private final AuthenticationService authenticationService;
    private final FridgeService fridgeService;

    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication token", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponseDTO.class
                            )
                    )
            })
    })
    public ResponseEntity<Object> register(@ParameterObject @RequestBody UserRegisterDTO user) {
        logger.info("User " + user.username() + " is being registered!");
        AuthenticationResponseDTO authenticationResponseDTO =  authenticationService.register(user);

        logger.info("A fridge is being initialized for " + user.username());
        fridgeService.initializeFridge(user.username());

        logger.info("Transaction completed successfully!");
        return ResponseEntity.ok(authenticationResponseDTO);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication token", content = {
                    @Content(mediaType = "application/json", schema = @Schema(
                            implementation = AuthenticationResponseDTO.class
                    ))
            })
    })
    public ResponseEntity<AuthenticationResponseDTO> register(@ParameterObject @RequestBody UserLoginDTO userLoginDTO) {
        logger.info("New Authentication request: " + userLoginDTO.toString());
        return ResponseEntity.ok(authenticationService.authenticate(userLoginDTO));
    }

    @PutMapping(
            value = "/update/user",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(summary = "Update user")
    public ResponseEntity<Object> update(@RequestPart UserUpdateDTO userUpdateDTO,
                                         @RequestParam(value = "picture", required = false) MultipartFile picture,
                                         Authentication authentication) throws IOException {
        logger.info(String.format("User %s wants to be updated!", userUpdateDTO.username()));
        //TODO: logic
        logger.info(String.format("User %s has been updated!", userUpdateDTO.username()));

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/picture")
    @Operation(summary = "Update user profile picture")
    public ResponseEntity<Object> updatePicture(@RequestParam(value = "picture") MultipartFile picture,
                                                Authentication authentication) throws IOException {

        logger.info(String.format("User %s wants to be updated!", authentication.getName()));
        fileStorageService.storeProfilePicture(jwtService.getAuthenticatedUserId().toString(), picture);
        logger.info(String.format("User %s has been updated!", authentication.getName()));
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/update/password")
    @Operation(summary = "Update user")
    public ResponseEntity<Object> updatePassword(@ParameterObject @RequestBody UserPasswordUpdateDTO passwordUpdateDTO,
                                                 Authentication authentication) throws IOException {

        logger.info(String.format("User %s wants to been updated!", authentication.getName()));
        userService.updateUserPassword(passwordUpdateDTO, authentication.getName());
        return ResponseEntity.ok().build();
    }
}