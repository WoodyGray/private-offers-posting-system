package org.senla.woodygray.PHOTO;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Slf4j
public class PhotoEncodeTest {

    @Test
    void testPhotoEncode() throws IOException {
        File file = new File("G:\\\\java\\private-offers-posting-system\\game_pad_1.jpg");
        byte[] fileContent = Files.readAllBytes(file.toPath());
        log.info(String.valueOf(fileContent.length));
        log.info(Base64.getEncoder().encodeToString(fileContent));
        log.info(" ");
        file = new File("G:\\\\java\\private-offers-posting-system\\game_pad_2.jpg");
        fileContent = Files.readAllBytes(file.toPath());
        log.info(String.valueOf(fileContent.length));
        log.info(Base64.getEncoder().encodeToString(fileContent));
    }
}
