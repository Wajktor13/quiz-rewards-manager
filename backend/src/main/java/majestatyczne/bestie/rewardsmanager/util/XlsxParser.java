package majestatyczne.bestie.rewardsmanager.util;

import lombok.Getter;
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
@Getter
public class XlsxParser {

    private final XlsxParserProperties properties;

    private ParsedData parsedData;

    public ParsedData parseSheet(Sheet sheet) {
        // two last columns are cut because they are empty! Thus row length is 17, not 19 (like the header)

        parsedData = new ParsedData();

        System.out.println("[XlsxParser] parsing data...");

        int rowLength = sheet.getRow(0).getLastCellNum();

        parseRewards(sheet);

        // parse rows
        for (Row row : sheet) {
            Date startDate = row.getCell(properties.getStartDateIndex()).getDateCellValue();
            Date endDate = row.getCell(properties.getEndDateIndex()).getDateCellValue();
            int score = (int) row.getCell(properties.getScoreIndex()).getNumericCellValue();
            String personName = row.getCell(rowLength + properties.getPersonNameIndex()).getStringCellValue();
            String allRewardsWithDescriptions = row.getCell(rowLength + properties.getRewardsIndex())
                    .getStringCellValue();

            Person person = parseAndGetPerson(personName);
            parseResult(person, score, startDate, endDate);
            parsePreference(person, allRewardsWithDescriptions);
        }

        parseQuiz(sheet);

        System.out.println("[XlsxParser] data parsing complete");

        return parsedData;
    }

    private void parseRewards(Sheet sheet) {
        Row firstRow = sheet.getRow(0);
        int rowLength = firstRow.getLastCellNum();
        String allRewardsWithDescriptions = firstRow.getCell(rowLength + properties.getRewardsIndex())
                .getStringCellValue();
        List<String[]> convertedRewardsWithDescriptions =
                convertRewardsWithDescription(allRewardsWithDescriptions);

        for (String[] convertedReward : convertedRewardsWithDescriptions) {
            Reward reward = new Reward();
            reward.setName(convertedReward[0]);
            reward.setDescription(convertedReward[1]);
            parsedData.getRewards().add(reward);
        }
    }

    private Person parseAndGetPerson(String personName) {
        Person person = new Person();
        person.setName(personName);
        parsedData.getPeople().add(person);

        return person;
    }

    private void parseResult(Person person, int score, Date startDate, Date endDate) {
        Result result = new Result();
        result.setQuiz(parsedData.getQuiz());
        result.setScore(score);
        result.setPerson(person);
        result.setStartDate(startDate);
        result.setEndDate(endDate);
        parsedData.getResults().add(result);
    }

    private void parsePreference(Person person, String allRewardsWithDescriptions) {
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

    private void parseQuiz(Sheet sheet) {
        Row row = sheet.getRow(0);
        Date date = row.getCell(properties.getStartDateIndex()).getDateCellValue();
        int maxScore = (row.getLastCellNum() - properties.getNotEvaluatedQuestions())
                / properties.getColumnsPerEvaluatedQuestion();

        parsedData.getQuiz().setDate(date);
        parsedData.getQuiz().setMaxScore(maxScore);
        parsedData.getQuiz().setResults(parsedData.getResults()); // results must be parsed first!
        parsedData.getQuiz().setName(sheet.getSheetName());
    }

    private List<String[]> convertRewardsWithDescription(String rewardsWithDescription) {
        List<String[]> convertedRewards = new ArrayList<>();
        List<String> split1 = new ArrayList<>(List.of(rewardsWithDescription.split(";")));

        for (String rewardWithDescription : split1) {
            List<String> split2 = new ArrayList<>(List.of(rewardWithDescription.split("[()]")));
            String reward = split2.get(0).substring(0, split2.get(0).length() - 1); // cut last char - whitespace
            String description = split2.get(1);

            convertedRewards.add(new String[]{reward, description});
        }

        return convertedRewards;
    }
}
