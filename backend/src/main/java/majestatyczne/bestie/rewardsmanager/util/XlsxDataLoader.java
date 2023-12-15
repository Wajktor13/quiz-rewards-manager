package majestatyczne.bestie.rewardsmanager.util;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.service.*;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
@RequiredArgsConstructor
public class XlsxDataLoader implements FileDataLoader {

    private final XlsxParser xlsxParser;
    private final PersonService personService;
    private final PreferenceService preferenceService;
    private final QuizService quizService;
    private final ResultService resultService;
    private final RewardService rewardService;
    private ParsedData parsedData;

    @Override
    public void loadData(MultipartFile multipartFile) throws IOException {

        System.out.println("[XlsxDataLoader] loading data...");

        BufferedInputStream bufferedInputStream = new BufferedInputStream(multipartFile.getInputStream());
        Workbook workbook = new XSSFWorkbook(bufferedInputStream);

        if (workbook.getNumberOfSheets() < 1) {
            // workbook has no sheets
            throw new EmptyFileException();
        }

        Sheet sheet = workbook.getSheetAt(0);
        workbook.setSheetName(0, multipartFile.getOriginalFilename());

        if (sheet.getLastRowNum() < 1) {
            // sheet is empty
            throw new EmptyFileException();
        }

        sheet.shiftRows(1, sheet.getLastRowNum(), -1); // remove the header row

        parsedData = xlsxParser.parseSheet(sheet);

        loadQuiz();
        loadPeople();
        loadRewards();
        loadPreferences();
        loadResults();

        System.out.println("[XlsxDataLoader] data loading complete");
    }

    private void loadQuiz() {
        quizService.addQuiz(parsedData.getQuiz());
    }

    private void loadPeople() {
        personService.addPeople(parsedData.getPeople());
    }

    private void loadRewards() {
        rewardService.addRewards(parsedData.getRewards());
    }

    private void loadPreferences() {
        preferenceService.addPreferences(parsedData.getPreferences());
    }

    private void loadResults() {
        resultService.addResults(parsedData.getResults());
    }

}
