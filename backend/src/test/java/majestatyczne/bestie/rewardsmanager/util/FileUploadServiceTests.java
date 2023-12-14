package majestatyczne.bestie.rewardsmanager.util;

import majestatyczne.bestie.rewardsmanager.service.FileUploadService;
import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FileUploadServiceTests {

    @Autowired
    private FileUploadService fileUploadService;

    @Test
    public void testLoadValidNotEmptyFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("resources/example.xlsx");
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "xlsx file",
                "test_file.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                fileInputStream
        );

        assertDoesNotThrow(() -> fileUploadService.loadFile(mockMultipartFile));
    }

    @Test
    public void testLoadNotValidNotEmptyFile() {
        MultipartFile mockMultipartFile1 = new MockMultipartFile(
                "data",
                "test_file1.txt",
                "text/plain",
                "some data".getBytes()
        );

        assertThrows(ResponseStatusException.class, () -> fileUploadService.loadFile(mockMultipartFile1));

        MultipartFile mockMultipartFile2 = new MockMultipartFile(
                "data",
                "test_file2.7z",
                "application/x-7z-compressed",
                "dwedwedwefweifjwofhwrhgwehgurwehguiehrgurhguiwerihwruegherwuhig".getBytes()
        );

        assertThrows(ResponseStatusException.class, () -> fileUploadService.loadFile(mockMultipartFile2));
    }

    @Test
    public void testLoadValidEmptyFile() {
        byte[] emptyByteArray = {};

        MultipartFile mockMultipartFile1 = new MockMultipartFile(
                "xlsx file",
                "test_file.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                emptyByteArray
        );

        assertThrows(ResponseStatusException.class, () -> fileUploadService.loadFile(mockMultipartFile1));
    }

    @Test
    public void testLoadNotValidEmptyFile() {
        byte[] emptyByteArray = {};

        MultipartFile mockMultipartFile1 = new MockMultipartFile(
                "data",
                "test_file1.txt",
                "text/plain",
                emptyByteArray
        );

        assertThrows(ResponseStatusException.class, () -> fileUploadService.loadFile(mockMultipartFile1));
    }
}