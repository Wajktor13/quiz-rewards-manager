package majestatyczne.bestie.rewardsmanager.util;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.service.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@RequiredArgsConstructor
public class XlsxDataLoader implements FileDataLoader {

    private String xlsxFilePath;
    private final XlsxParser xlsxParser;
    private final PersonService personService;
    private final PreferenceService preferenceService;
    private final QuizService quizService;
    private final ResultService resultService;
    private final RewardService rewardService;

    @Override
    public void setInputFilePath(String inputFilePath) {
        this.xlsxFilePath = inputFilePath;
    }

    @Override
    public void loadData() {
        try (FileInputStream file = new FileInputStream(this.xlsxFilePath)) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet.getLastRowNum() < 1) {
                // sheet is empty
                return;
            }

            sheet.shiftRows(1, sheet.getLastRowNum(), -1); // remove the header row

            ParsedData parsedData = this.xlsxParser.parseSheet(sheet);

            loadQuiz(parsedData);
            loadPeople(parsedData);
            loadRewards(parsedData);
            loadPreferences(parsedData);
            loadResults(parsedData);

            System.out.println("[XlsxDataLoader] data loading complete");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQuiz(ParsedData parsedData) {
        quizService.addQuiz(parsedData.getQuiz());
    }

    private void loadPeople(ParsedData parsedData) {
        parsedData.getPeople().forEach(personService::addPerson);
    }

    private void loadRewards(ParsedData parsedData) {
        parsedData.getRewards().forEach(rewardService::addReward);
    }

    private void loadPreferences(ParsedData parsedData) {
        parsedData.getPreferences().forEach(preferenceService::addPreference);
    }

    private void loadResults(ParsedData parsedData) {
        parsedData.getResults().forEach(resultService::addResult);
    }
}
