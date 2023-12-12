package majestatyczne.bestie.rewardsmanager.util;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.Person;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import majestatyczne.bestie.rewardsmanager.service.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Optional;

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
    public void loadData(MultipartFile multipartFile) {

        System.out.println("[XlsxDataLoader] loading data...");

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(multipartFile.getInputStream())) {
            Workbook workbook = new XSSFWorkbook(bufferedInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet.getLastRowNum() < 1) {
                // sheet is empty
                return;
            }

            sheet.shiftRows(1, sheet.getLastRowNum(), -1); // remove the header row

            parsedData = this.xlsxParser.parseSheet(sheet);

            loadQuiz();
            loadPeople();
            loadRewards();
            loadPreferences();
            loadResults();

            System.out.println("[XlsxDataLoader] data loading complete");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQuiz() {
        quizService.addQuiz(parsedData.getQuiz());
    }

    private void loadPeople() {
        updatePeople();
        parsedData.getPeople().forEach(personService::addPerson);
    }

    private void loadRewards() {
        updateRewards();
        parsedData.getRewards().forEach(rewardService::addReward);
    }

    private void loadPreferences() {
        updatePreferences();
        parsedData.getPreferences().forEach(preferenceService::addPreference);
    }

    private void loadResults() {
        updateResults();
        parsedData.getResults().forEach(resultService::addResult);
    }

    private void updatePeople() {
        List<Person> peopleReplaced = parsedData.getPeople()
                .stream()
                .map((person -> personService.findPersonByName(person.getName()).orElse(person)))
                .toList();

        parsedData.setPeople(peopleReplaced);
    }

    private void updateRewards() {
        List<Reward> rewardsReplaced = parsedData.getRewards()
                .stream()
                .map((reward -> rewardService.findRewardByName(reward.getName()).orElse(reward)))
                .toList();

        parsedData.setRewards(rewardsReplaced);
    }

    private void updatePreferences() {
        parsedData.getPreferences().forEach((preference -> {
            Optional<Reward> reward = rewardService.findRewardByName(preference.getReward().getName());
            Optional<Person> person = personService.findPersonByName(preference.getPerson().getName());

            if (reward.isPresent() && person.isPresent()) {
                preference.setReward(reward.get());
                preference.setPerson(person.get());
            }
        }));
    }

    private void updateResults() {
        parsedData.getResults().forEach((result -> {
            Optional<Person> person = personService.findPersonByName(result.getPerson().getName());
            person.ifPresent(result::setPerson);
        }));
    }
}
