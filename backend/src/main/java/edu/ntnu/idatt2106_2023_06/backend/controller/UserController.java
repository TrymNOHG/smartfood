package edu.ntnu.idatt2106_2023_06.backend.controller;

import edu.ntnu.idatt2106_2023_06.backend.dto.security.AuthenticationResponseDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.users.*;
import edu.ntnu.idatt2106_2023_06.backend.exception.UnauthorizedException;
import edu.ntnu.idatt2106_2023_06.backend.service.files.FileStorageService;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.AuthenticationService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import edu.ntnu.idatt2106_2023_06.backend.service.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

/**
 * UserController handles HTTP requests related to user management and authentication.
 * This controller provides endpoints for user registration, login, and various user operations such as updating
 * profile information, uploading and retrieving profile pictures, and changing the user's password.
 *
 * @author Brage H. Kvamme, Trym Gudvangen
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Handles user management and authentication")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final AuthenticationService authenticationService;
    private final FridgeService fridgeService;

    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final JwtService jwtService;

    /**
     * Registers a new user.
     *
     * @param user The UserRegisterDTO object containing the user's registration details.
     * @return ResponseEntity containing the generated authentication token upon successful registration.
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully and received an authentication token.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponseDTO.class),
                            examples = @ExampleObject(value = "{\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\"}"))),
            @ApiResponse(responseCode = "400", description = "User already exists.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthenticationResponseDTO.class),
                    examples = @ExampleObject(value = "{\"message\":\"Username already exists!\"}")
            ))
    })
    public ResponseEntity<Object> register(@ParameterObject @RequestBody UserRegisterDTO user) throws MessagingException {
        logger.info("User " + user.username() + " is being registered!");
        AuthenticationResponseDTO authenticationResponseDTO =  authenticationService.register(user);

        logger.info("A fridge is being initialized for " + user.username());
        fridgeService.initializeFridge(user.username());

        logger.info("Transaction completed successfully!");
        return ResponseEntity.ok(authenticationResponseDTO);
    }

    /**
     * Authenticates a user with login information.
     *
     * @param userLoginDTO The UserLoginDTO object containing the user's login details.
     * @return ResponseEntity containing the generated authentication token upon successful authentication.
     */
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

    /**
     * Updates the user's text information.
     *
     * @param userUpdateDTO The UserUpdateDTO object containing the user's updated information.
     * @return ResponseEntity indicating the success of the update operation.
     */
    @PutMapping(value = "/update/info")
    @Operation(summary = "Update user text information")
    @ApiResponse(responseCode = "200", description = "User text information updated successfully.")
    public ResponseEntity<String> update(@RequestBody UserUpdateDTO userUpdateDTO,
                                         Authentication authentication) {
        logger.info(String.format("User %s wants to be updated!", userUpdateDTO.username()));
        String token = userService.updateUser(userUpdateDTO);
        logger.info(String.format("User %s has been updated!", userUpdateDTO.username()));

        return ResponseEntity.ok(token);
    }

    /**
     * Updates the user's profile picture.
     *
     * @param picture The MultipartFile containing the user's new profile picture.
     * @param authentication The authentication object containing the user's authentication details.
     * @return ResponseEntity indicating the success of the update operation.
     */
    @PutMapping("/update/picture")
    @Operation(summary = "Update user profile picture")
    @ApiResponse(responseCode = "200", description = "User profile picture updated successfully.")
    public ResponseEntity<Object> updatePicture(@RequestParam(value = "picture") MultipartFile picture,
                                                Authentication authentication) throws IOException {
        logger.info(String.format("User %s wants to be updated!", authentication.getName()));
        fileStorageService.storeProfilePicture(jwtService.getAuthenticatedUserId().toString(), picture);
        logger.info(String.format("User %s has been updated!", authentication.getName()));
        return ResponseEntity.ok().build();
    }

    /**
     * Updates the user's password.
     *
     * @param passwordUpdateDTO The UserPasswordUpdateDTO object containing the user's new password.
     * @param authentication The authentication object containing the user's authentication details.
     * @return ResponseEntity indicating the success of the update operation.
     */
    @PutMapping(value = "/update/password")
    @Operation(summary = "Update user")
    @ApiResponse(responseCode = "200", description = "User password updated successfully.")
    public ResponseEntity<Object> updatePassword(@ParameterObject @RequestBody UserPasswordUpdateDTO passwordUpdateDTO,
                                                 Authentication authentication) {

        logger.info(String.format("User %s wants to been updated!", authentication.getName()));
        userService.updateUserPassword(passwordUpdateDTO, authentication.getName());
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves the user's profile picture.
     *
     * @param authentication The authentication object containing the user's authentication details.
     * @return ResponseEntity containing the user's profile picture as a byte array.
     */
    @GetMapping("/get/picture")
    @Operation(summary = "Get user profile picture")
    @ApiResponse(responseCode = "200", description = "User profile picture retrieved successfully.", content = @Content(
            mediaType = "image/jpeg",
            schema = @Schema(implementation = byte[].class)))
    public ResponseEntity<Object> getPicture(Authentication authentication) throws IOException {
        logger.info(String.format("User %s wants to get their profile picture!", authentication.getName()));
        byte[] file = fileStorageService.getProfilePicture(jwtService.getAuthenticatedUserId());
        if(file == null)
            return ResponseEntity.ok("No profile picture found!");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+jwtService.getAuthenticatedUserId()+"\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

    /**
     * Retrieves the user's profile picture from ID.
     *
     * @return ResponseEntity containing the user's profile picture as a byte array.
     */
    @GetMapping("/get/picture/{id}")
    @Operation(summary = "Get user profile picture from ID")
    @ApiResponse(responseCode = "200", description = "User profile picture retrieved successfully.", content = @Content(
            mediaType = "image/jpeg",
            schema = @Schema(implementation = byte[].class)))
    public ResponseEntity<Object> getPictureFromId(@PathVariable Long id) throws IOException {
        logger.info(String.format("Getting the profile picture of user with ID %d!", id));
        byte[] file = fileStorageService.getProfilePicture(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+jwtService.getAuthenticatedUserId()+"\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

    /**
     * Retrieves the user's text information.
     *
     * @param authentication The authentication object containing the user's authentication details.
     * @return ResponseEntity containing the user's text information.
     */
    @GetMapping("/get/info")
    @Operation(summary = "Get user text information")
    @ApiResponse(responseCode = "200", description = "User text information retrieved successfully.", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = UserLoadDTO.class),
            examples = @ExampleObject(value = "{\"username\":\"user\",\"email\":\"test.test@gmail.com\",\"firstName\":\"Test\",\"lastName\":\"Test\"}")))
    public ResponseEntity<Object> getInfo(Authentication authentication) {
        logger.info(String.format("User %s wants to get their text information!", authentication.getName()));
        logger.info(userService.loadUser().toString());
        return ResponseEntity.ok(userService.loadUser());
    }

    /**
     * Deletes the user's profile picture.
     *
     * @param authentication The authentication object containing the user's authentication details.
     * @return ResponseEntity indicating the success of the delete operation.
     */
    @DeleteMapping("/delete/picture")
    @Operation(summary = "Delete user profile picture")
    @ApiResponse(responseCode = "200", description = "User text information retrieved successfully.", content = @Content(
            mediaType = "application/text",
            examples = @ExampleObject(value = "Picture deleted")
    ))
    public ResponseEntity<Object> deletePicture(Authentication authentication) throws IOException {
        logger.info(String.format("User %s wants to delete their profile picture!", authentication.getName()));
        fileStorageService.deleteProfilePicture();
        return ResponseEntity.ok("Picture deleted");
    }

    /**
     * Searches for a user by username.
     *
     * @param username The username of the user to be searched for.
     * @return ResponseEntity containing the user's text information.
     */
    @GetMapping("/search/{username}")
    @Operation(summary = "Search for a user by username")
    @ApiResponse(responseCode = "200", description = "User text information retrieved successfully.", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = UserLoadDTO.class),
            examples = @ExampleObject(value = "{\"username\":\"user\",\"id\":\"69\"}")))
    public ResponseEntity<Object> search(@PathVariable String username) {
        logger.info(String.format("Searching for %s", username));
        return ResponseEntity.ok(userService.searchUser(username.toLowerCase()));
    }

    /**
     * This method checks whether a user with the given fridge id.
     *
     * @param fridgeId          The id of the fridge to be checked
     * @param authentication    The authentication of the user.
     * @return                  ResponseEntity containing the user's superuser status.
     */
    @GetMapping("/superuser")
    @Operation(summary = "Search for a user's superuser status in a given fridge by fridgeId")
    @ApiResponse(responseCode = "200", description = "User superuser status retrieved successfully.", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Boolean.class),
            examples = @ExampleObject(value = "{\"isSuperUser\":\"true\"}")))
    public ResponseEntity<Boolean> search(@ParameterObject @RequestParam(name="fridgeId") Long fridgeId,
                                          Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) throw new UnauthorizedException("Anon");

        logger.info(String.format("Check the super user status for %s", authentication.getName()));
        return ResponseEntity.ok(userService.isSuperUser(fridgeId, authentication.getName()));
    }
}