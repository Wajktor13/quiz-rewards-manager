package majestatyczne.bestie.rewardsmanager.util;

import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class XlsxDataLoaderTests {

    @Autowired
    private XlsxDataLoader xlsxDataLoader;

    @Test
    public void testOpenValidNotEmptyFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("resources/example.xlsx");
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "xlsx file",
                "test_file.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                fileInputStream
        );

        assertDoesNotThrow(() -> xlsxDataLoader.loadData(mockMultipartFile));
    }

    @Test
    public void testOpenNotValidNotEmptyFile() {
        MultipartFile mockMultipartFile1 = new MockMultipartFile(
                "data",
                "test_file1.txt",
                "text/plain",
                "some data".getBytes()
        );

        assertThrows(NotOfficeXmlFileException.class, () -> xlsxDataLoader.loadData(mockMultipartFile1));

        MultipartFile mockMultipartFile2 = new MockMultipartFile(
                "data",
                "test_file2.7z",
                "application/x-7z-compressed",
                "dwedwedwefweifjwofhwrhgwehgurwehguiehrgurhguiwerihwruegherwuhig".getBytes()
        );

        assertThrows(NotOfficeXmlFileException.class, () -> xlsxDataLoader.loadData(mockMultipartFile2));
    }

    @Test
    public void testOpenValidEmptyFile() {
        byte[] emptyByteArray = {};

        MultipartFile mockMultipartFile1 = new MockMultipartFile(
                "xlsx file",
                "test_file.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                emptyByteArray
        );

        assertThrows(EmptyFileException.class, () -> xlsxDataLoader.loadData(mockMultipartFile1));
    }

    @Test
    public void testOpenNotValidEmptyFile() {
        byte[] emptyByteArray = {};

        MultipartFile mockMultipartFile1 = new MockMultipartFile(
                "data",
                "test_file1.txt",
                "text/plain",
                emptyByteArray
        );

        assertThrows(EmptyFileException.class, () -> xlsxDataLoader.loadData(mockMultipartFile1));
    }
}
