package majestatyczne.bestie.rewardsmanager.controller;

import majestatyczne.bestie.rewardsmanager.service.*;
import majestatyczne.bestie.rewardsmanager.util.ParsedData;
import majestatyczne.bestie.rewardsmanager.util.XlsxDataLoader;
import majestatyczne.bestie.rewardsmanager.util.XlsxParser;
import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class XlsxDataLoaderTest {

    @Mock
    private XlsxParser xlsxParser;
    @Mock
    private PersonService personService;
    @Mock
    private PreferenceService preferenceService;
    @Mock
    private QuizService quizService;
    @Mock
    private ResultService resultService;
    @Mock
    private RewardService rewardService;
    @InjectMocks
    private XlsxDataLoader xlsxDataLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidNotEmptyFile() throws IOException {
        when(xlsxParser.parseSheet(any(Sheet.class))).thenReturn(new ParsedData());

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TestSheet");

        // create rows
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Column 1");
        headerRow.createCell(1).setCellValue("Column 2");

        Row dataRow1 = sheet.createRow(1);
        dataRow1.createCell(0).setCellValue("Data 1A");
        dataRow1.createCell(1).setCellValue("Data 1B");

        Row dataRow2 = sheet.createRow(2);
        dataRow2.createCell(0).setCellValue("Data 2A");
        dataRow2.createCell(1).setCellValue("Data 2B");

        // write the workbook content to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] content = outputStream.toByteArray();

        // create a MockMultipartFile with the workbook content
        MultipartFile mockMultipartFile = new MockMultipartFile(
                "xlsx file",
                "test_file.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                content);

        assertDoesNotThrow(() -> xlsxDataLoader.loadData(mockMultipartFile));
    }

    @Test
    public void testNotValidNotEmptyFile() {
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
    public void testValidEmptyFile() {
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
    public void testNotValidEmptyFile() {
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
