package com.app.RestaurantApp.users;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.xmlunit.builder.Input;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.app.RestaurantApp.users.appUser.Constants.TEST_PFP_FILENAME;
import static com.app.RestaurantApp.users.appUser.Constants.TEST_PFP_PATH;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUploadUtilTest {

    public static MultipartFile getMultipartTestFile(){
        Path path = Paths.get(TEST_PFP_PATH);
        String contentType = "image/jpeg";
        byte[] content = null;

        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            return null;
        }
        return new MockMultipartFile(TEST_PFP_FILENAME, TEST_PFP_FILENAME, contentType, content);
    }

    public static InputStream getTestFileInputStream(){
        Path path = Paths.get(TEST_PFP_PATH);
        String contentType = "image/jpeg";
        byte[] content = null;

        try {
            return Files.newInputStream(path);
        } catch (final IOException e) {
            return null;
        }
    }

    @Test
    public void testSaveFile() throws IOException {
        MultipartFile multipartFile = getMultipartTestFile();
        if (multipartFile == null) return;

        String savedPath = FileUploadUtil.saveFile("user_profile_photos", "default.jpg", multipartFile);

        Path resultPath = Paths.get("user_profile_photos/default.jpg");
        assertTrue(resultPath.toFile().exists() && !resultPath.toFile().isDirectory());
        assertTrue(resultPath.toFile().delete());
    }
}
