package majestatyczne.bestie.rewardsmanager.util;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.Person;
import majestatyczne.bestie.rewardsmanager.model.Preference;
import majestatyczne.bestie.rewardsmanager.model.Result;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class XlsxParser {

    private final ParsedData parsedData;

    public ParsedData parseSheet(Sheet sheet) {
        /*
        todo: assumptions (if hardcoded columns indices stay)

        two last columns are cut because they are empty! Thus row length is 17, not 19 (like the header)
         */

        parseRewards(sheet);
        parseEntries(sheet);
        parseQuiz(sheet);

        System.out.println("[XlsxParser] data parsing complete");

        return parsedData;
    }

    private void parseRewards(Sheet sheet) {
        Row row = sheet.getRow(0);
        String allRewardsWithDescriptions = row.getCell(row.getLastCellNum() - 1).getStringCellValue();
        List<String[]> convertedRewardsWithDescriptions =
                convertRewardsWithDescription(allRewardsWithDescriptions);

        for (String[] convertedReward : convertedRewardsWithDescriptions) {
            Reward reward = new Reward();
            reward.setName(convertedReward[0]);
            reward.setDescription(convertedReward[1]);
            parsedData.getRewards().add(reward);
        }
    }

    private void parseEntries(Sheet sheet) {
        int rowLength = sheet.getRow(0).getLastCellNum();

        for (Row row : sheet) {
            Date startDate = row.getCell(1).getDateCellValue();
            Date endDate = row.getCell(2).getDateCellValue();
            int score = (int) row.getCell(5).getNumericCellValue();
            String personName = row.getCell(rowLength - 4).getStringCellValue();
            String allRewardsWithDescriptions = row.getCell(row.getLastCellNum() - 1).getStringCellValue();

            // Person
            Person person = new Person();
            person.setName(personName);
            parsedData.getPeople().add(person);

            // Result
            Result result = new Result();
            result.setQuiz(parsedData.getQuiz());
            result.setScore(score);
            result.setPerson(person);
            result.setStartDate(startDate);
            result.setEndDate(endDate);
            parsedData.getResults().add(result);

            // Preference
            List<String[]> convertedRewardsWithDescriptions =
                    convertRewardsWithDescription(allRewardsWithDescriptions);

            for (String[] convertedReward : convertedRewardsWithDescriptions) {
                String rewardName = convertedReward[0];
                Reward reward = parsedData.getRewards()
                        .stream()
                        .filter(r -> r.getName().equals(rewardName))
                        .findFirst()
                        .orElse(new Reward());

                Preference preference = new Preference();
                preference.setQuiz(parsedData.getQuiz());
                preference.setPerson(person);
                preference.setPriority(convertedRewardsWithDescriptions.indexOf(convertedReward));
                preference.setReward(reward);
                parsedData.getPreferences().add(preference);
            }

        }
    }

    private void parseQuiz(Sheet sheet) {
        Row row = sheet.getRow(0);

        Date date = row.getCell(1).getDateCellValue();

        /*
        -11: 11 cells are not related to quiz questions that are evaluated;
        /3: 3 cells are assigned to each quiz question that is evaluated
        assumption: for each question max score is 1
         */
        int maxScore = (row.getLastCellNum() - 11) / 3;

        parsedData.getQuiz().setDate(date);
        parsedData.getQuiz().setMaxScore(maxScore);
        parsedData.getQuiz().setResults(parsedData.getResults()); // results must be parsed first!
        parsedData.getQuiz().setName("Quiz z dnia " + date);
    }

    private List<String[]> convertRewardsWithDescription(String rewardsWithDescription) {
        List<String[]> convertedRewards = new ArrayList<>();
        List<String> split1 = new ArrayList<>(List.of(rewardsWithDescription.split(";")));

        for (String rewardWithDescription : split1) {
            List<String> split2 = new ArrayList<>(List.of(rewardWithDescription.split("[()]")));
            String reward = split2.get(0);
            String description = split2.get(1);

            convertedRewards.add(new String[]{reward, description});
        }

        return convertedRewards;
    }
}
