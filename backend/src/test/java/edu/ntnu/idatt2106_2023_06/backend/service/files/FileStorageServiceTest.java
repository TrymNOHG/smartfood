package edu.ntnu.idatt2106_2023_06.backend.service.files;

import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.ImageNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class FileStorageServiceTest {

    @Autowired
    private FileStorageService fileStorageService;

    @MockBean
    private JwtService jwtServiceMock;

    @MockBean
    private MultipartFile profilePictureMock;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        Path fileStorageLocation = tempDir.resolve("images").toAbsolutePath().normalize();
        Files.createDirectories(fileStorageLocation);
        fileStorageService.setFileStorageLocation(fileStorageLocation);
    }

    /*@Test
    void storeProfilePicture() throws IOException {
        String userId = "12345";
        when(profilePictureMock.isEmpty()).thenReturn(false);
        when(profilePictureMock.getInputStream()).thenReturn()

        fileStorageService.storeProfilePicture(userId, profilePictureMock);

        Path storedFile = tempDir.resolve("images").resolve(userId);
        assertTrue(Files.exists(storedFile));
    }*/

    // TODO: fix this test
    /*@Test
    void deleteProfilePicture() throws IOException {
        String userId = "12345";
        Path storedFile = tempDir.resolve("images").resolve(userId);
        Files.createFile(storedFile);
        when(jwtServiceMock.getAuthenticatedUserId()).thenReturn(Long.parseLong(userId));

        fileStorageService.deleteProfilePicture();

        assertFalse(Files.exists(storedFile));
    }*/

    @Test
    void getProfilePicture() throws IOException {
        long imageId = 12345L;
        Path storedFile = tempDir.resolve("images").resolve(String.valueOf(imageId));
        Files.copy(Paths.get("src/test/resources/test-image.jpg"), storedFile);

        byte[] expectedBytes = Files.readAllBytes(storedFile);
        byte[] actualBytes = fileStorageService.getProfilePicture(imageId);

        assertArrayEquals(expectedBytes, actualBytes);
    }
    /*
    TODO: Brage fix test plz!
    @Test
    void getProfilePictureThrowsImageNotFoundException() {
        long imageId = 12345L;

        assertThrows(ImageNotFoundException.class, () -> {
            fileStorageService.getProfilePicture(imageId);
        });
    }

     */

}
