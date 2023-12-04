package majestatyczne.bestie.rewardsmanager.util;

import majestatyczne.bestie.rewardsmanager.model.Person;
import majestatyczne.bestie.rewardsmanager.model.Preference;
import majestatyczne.bestie.rewardsmanager.model.Result;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import majestatyczne.bestie.rewardsmanager.service.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class XlsxDataLoader implements IFileDataLoader {

    private String xlsxFilePath = "";
    private final XlsxParser xlsxParser = new XlsxParser();
    private final PersonService personService;
    private final PreferenceService preferenceService;
    private final QuizService quizService;
    private final ResultService resultService;
    private final RewardService rewardService;

    public XlsxDataLoader(PersonService personService, PreferenceService preferenceService, QuizService quizService,
                          ResultService resultService, RewardService rewardService) {
        this.personService = personService;
        this.preferenceService = preferenceService;
        this.quizService = quizService;
        this.resultService = resultService;
        this.rewardService= rewardService;
    }

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

            this.loadQuiz(parsedData);
            this.loadPeople(parsedData);
            this.loadRewards(parsedData);
            this.loadPreferences(parsedData);
            this.loadResults(parsedData);

            System.out.println("[XlsxDataLoader] data loading complete");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQuiz(ParsedData parsedData) {
        this.quizService.addQuiz(parsedData.quiz);
    }

    private void loadPeople(ParsedData parsedData) {
        for (Person person : parsedData.people) {
            this.personService.addPerson(person);
        }
    }

    private void loadRewards(ParsedData parsedData) {
        for (Reward reward : parsedData.rewards) {
            this.rewardService.addReward(reward);
        }
    }

    private void loadPreferences(ParsedData parsedData) {
        for (Preference preference : parsedData.preferences) {
            this.preferenceService.addPreference(preference);
        }
    }

    private void loadResults(ParsedData parsedData) {
        for (Result result : parsedData.results) {
            this.resultService.addResult(result);
        }
    }
}
