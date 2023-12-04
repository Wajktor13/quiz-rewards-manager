package majestatyczne.bestie.rewardsmanager.util;

import majestatyczne.bestie.rewardsmanager.model.Person;
import majestatyczne.bestie.rewardsmanager.model.Preference;
import majestatyczne.bestie.rewardsmanager.model.Result;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class XlsxParser {

    private final ParsedData parsedData = new ParsedData();

    public ParsedData parseSheet(Sheet sheet) {
        /*
        todo: assumptions (if hardcoded columns indices stay)

        two last columns are cut because they are empty! Thus row length is 17, not 19 (like the header)
         */

        this.parseRewards(sheet);
        this.parseEntries(sheet);
        this.parseQuiz(sheet);

        System.out.println("[XlsxParser] data parsing complete");

        return parsedData;
    }

    private void parseRewards(Sheet sheet) {
        Reward reward;
        Row row = sheet.getRow(0);
        String allRewardsWithDescriptions = row.getCell(row.getLastCellNum() - 1).getStringCellValue();
        ArrayList<String[]> convertedRewardsWithDescriptions =
                convertRewardsWithDescription(allRewardsWithDescriptions);

        for (String[] convertedReward : convertedRewardsWithDescriptions) {
            reward = new Reward();
            reward.setName(convertedReward[0]);
            reward.setDescription(convertedReward[1]);
            this.parsedData.rewards.add(reward);
        }
    }

    private void parseEntries(Sheet sheet) {
        int rowLength = sheet.getRow(0).getLastCellNum();

        for (Row row : sheet) {
            Date startDate =  row.getCell(1).getDateCellValue();
            Date endDate =  row.getCell(2).getDateCellValue();
            int score = (int) row.getCell(5).getNumericCellValue();
            String personName = row.getCell(rowLength - 4).getStringCellValue();
            String allRewardsWithDescriptions = row.getCell(row.getLastCellNum() - 1).getStringCellValue();

            // Person
            Person person = new Person();
            person.setName(personName);
            parsedData.people.add(person);

            // Result
            Result result = new Result();
            result.setQuiz(this.parsedData.quiz);
            result.setScore(score);
            result.setPerson(person);
            result.setStartDate(startDate);
            result.setEndDate(endDate);
            this.parsedData.results.add(result);

            // Preference
            String rewardName;
            Reward reward = new Reward();
            ArrayList<String[]> convertedRewardsWithDescriptions =
                    convertRewardsWithDescription(allRewardsWithDescriptions);

            for (String[] convertedReward : convertedRewardsWithDescriptions) {
                rewardName = convertedReward[0];

                for (Reward r : this.parsedData.rewards) { // rewards must be parsed first!
                    if (Objects.equals(r.getName(), rewardName)) {
                        reward = r;
                        break;
                    }
                }

                Preference preference = new Preference();
                preference.setQuiz(this.parsedData.quiz);
                preference.setPerson(person);
                preference.setPriority(convertedRewardsWithDescriptions.indexOf(convertedReward));
                preference.setReward(reward);
                this.parsedData.preferences.add(preference);
            }

        }
    }

    private void parseQuiz(Sheet sheet) {
        Row row = sheet.getRow(0);

        Date date = row.getCell(1).getDateCellValue();

        /*
        -11: 11 cells are not related to quiz questions that are evaluated;
        /3: 3 cells are assigned to each quiz question
        assumption: for each question max score is 0
         */
        int maxScore = (row.getLastCellNum() - 11) / 3;

        this.parsedData.quiz.setDate(date);
        this.parsedData.quiz.setMaxScore(maxScore);
        this.parsedData.quiz.setResults(parsedData.results); // results must be parsed first!
    }

    private ArrayList<String[]> convertRewardsWithDescription(String rewardsWithDescription) {
        ArrayList<String[]> convertedRewards = new ArrayList<>();
        ArrayList<String> split1 =  new ArrayList<>(List.of(rewardsWithDescription.split(";")));
        ArrayList<String> split2;
        String reward;
        String description;

        for (String rewardWithDescription: split1) {
            split2 = new ArrayList<>(List.of(rewardWithDescription.split("[()]")));
            reward = split2.get(0);
            description = split2.get(1);

            convertedRewards.add(new String[]{reward, description});
        }

        return convertedRewards;
    }
}
