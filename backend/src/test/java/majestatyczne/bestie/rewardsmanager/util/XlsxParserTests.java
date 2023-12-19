package majestatyczne.bestie.rewardsmanager.util;

import majestatyczne.bestie.rewardsmanager.RewardsManagerTestsConfiguration;
import majestatyczne.bestie.rewardsmanager.model.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class XlsxParserTests {

    @Autowired
    private RewardsManagerTestsConfiguration rewardsManagerTestsConfiguration;

    @Autowired
    private XlsxParser xlsxParser;

    private Sheet prepareSheet(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            Workbook workbook = new XSSFWorkbook(bufferedInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            sheet.shiftRows(1, sheet.getLastRowNum(), -1);

            return sheet;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testParsingOnShortValidFile1() throws ParseException {
        Sheet sheet = prepareSheet(rewardsManagerTestsConfiguration.getShortValidFile1Path());
        ParsedData parsedData = xlsxParser.parseSheet(sheet);

        // Person
        List<Person> people = parsedData.getPeople();

        assertEquals(3, people.size());

        List<Person> expectedPeople = Arrays.asList(
                new Person(0, "Baweł"),
                new Person(1, "Daredevilq"),
                new Person(2, "Schmetterling")
        );

        expectedPeople.forEach(expectedPerson ->
            assertTrue(people.stream().anyMatch(p -> p.getName().equals(expectedPerson.getName())))
        );

        // Reward
        List<Reward> rewards = parsedData.getRewards();

        assertEquals(4, rewards.size());

        List<Reward> expectedRewards = Arrays.asList(
                new Reward(0, new RewardCategory(), "Marchewka lab", "+10% do lab"),
                new Reward(1, new RewardCategory(), "Lekarstwo", "odrobienie 2pkt lab"),
                new Reward(2, new RewardCategory(), "Rabat na sianko", "darmowa kartkówka"),
                new Reward(3, new RewardCategory(), "Weterynarz", "odrobienie 1 kartkówki"));

        expectedRewards.forEach(expectedReward ->
            assertTrue(rewards.stream().anyMatch(r -> r.getName().equals(expectedReward.getName()) &&
                    r.getDescription().equals(expectedReward.getDescription()))));

        // Quiz
        Quiz quiz = parsedData.getQuiz();

        assertEquals(2, quiz.getMaxScore());

        // Preference
        List<Preference> preferences = parsedData.getPreferences();

        assertEquals(people.size() * rewards.size(), preferences.size());

        List<Preference> expectedPreferences = Arrays.asList(
                new Preference(
                        0,
                        quiz,
                        new Person(0, "Baweł"),
                        new Reward(0, new RewardCategory(), "Marchewka lab", "+10% do lab"),
                        0),
                new Preference(
                        1,
                        quiz,
                        new Person(0, "Baweł"),
                        new Reward(0, new RewardCategory(), "Lekarstwo", "odrobienie 2pkt lab"),
                        1),
                new Preference(
                        2,
                        quiz,
                        new Person(0, "Baweł"),
                        new Reward(0, new RewardCategory(), "Rabat na sianko", "darmowa kartkówka"),
                        2),
                new Preference(
                        3,
                        quiz,
                        new Person(0, "Baweł"),
                        new Reward(0, new RewardCategory(), "Weterynarz", "odrobienie 1 kartkówki"),
                        3),
                new Preference(
                        0,
                        quiz,
                        new Person(0, "Daredevilq"),
                        new Reward(0, new RewardCategory(), "Lekarstwo", "odrobienie 2pkt lab"),
                        0),
                new Preference(
                        1,
                        quiz,
                        new Person(0, "Daredevilq"),
                        new Reward(0, new RewardCategory(), "Marchewka lab", "+10% do lab"),
                        1),
                new Preference(
                        2,
                        quiz,
                        new Person(0, "Daredevilq"),
                        new Reward(0, new RewardCategory(), "Weterynarz", "odrobienie 1 kartkówki"),
                        2),
                new Preference(
                        3,
                        quiz,
                        new Person(0, "Daredevilq"),
                        new Reward(0, new RewardCategory(), "Rabat na sianko", "darmowa kartkówka"),
                        3),
                new Preference(
                        0,
                        quiz,
                        new Person(0, "Schmetterling"),
                        new Reward(0, new RewardCategory(), "Marchewka lab", "+10% do lab"),
                        0),
                new Preference(
                        1,
                        quiz,
                        new Person(0, "Schmetterling"),
                        new Reward(0, new RewardCategory(), "Rabat na sianko", "darmowa kartkówka"),
                        1),
                new Preference(
                        2,
                        quiz,
                        new Person(0, "Schmetterling"),
                        new Reward(0, new RewardCategory(), "Lekarstwo", "odrobienie 2pkt lab"),
                        2),
                new Preference(
                        3,
                        quiz,
                        new Person(0, "Schmetterling"),
                        new Reward(0, new RewardCategory(), "Weterynarz", "odrobienie 1 kartkówki"),
                        3)
        );

        expectedPreferences.forEach(expectedPreference ->
            assertTrue(preferences.stream().anyMatch(p -> p.getPriority() == expectedPreference.getPriority() &&
                    p.getPerson().getName().equals(expectedPreference.getPerson().getName()) &&
                    p.getReward().getName().equals(expectedPreference.getReward().getName()) &&
                    p.getReward().getDescription().equals(expectedPreference.getReward().getDescription())))
        );

        // Result
        List<Result> results = parsedData.getResults();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        assertEquals(3, results.size());

        List<Result> expectedResults = Arrays.asList(
                new Result(0,
                        quiz,
                        new Person(0, "Baweł"),
                        dateFormat.parse("09.11.2023  19:34:55"),
                        dateFormat.parse("09.11.2023  19:40:15"),
                        2),
                new Result(1,
                        quiz,
                        new Person(1, "Daredevilq"),
                        dateFormat.parse("09.11.2023  19:35:03"),
                        dateFormat.parse("09.11.2023  19:41:21"),
                        2),
                new Result(2,
                        quiz,
                        new Person(2, "Schmetterling"),
                        dateFormat.parse("09.11.2023  19:34:58"),
                        dateFormat.parse("09.11.2023  19:38:41"),
                        2)
            );

        expectedResults.forEach(expectedResult ->
            assertTrue(results.stream().anyMatch(r ->
                    r.getPerson().getName().equals(expectedResult.getPerson().getName()) &&
                    r.getStartDate().equals(expectedResult.getStartDate()) &&
                    r.getEndDate().equals(expectedResult.getEndDate()) &&
                    r.getScore() == expectedResult.getScore()))
        );
    }

    @Test
    public void testParsingOnShortValidFile2() throws ParseException {
        Sheet sheet = prepareSheet(rewardsManagerTestsConfiguration.getShortValidFile2Path());
        ParsedData parsedData = xlsxParser.parseSheet(sheet);

        // Person
        List<Person> people = parsedData.getPeople();

        assertEquals(3, people.size());

        List<Person> expectedPeople = Arrays.asList(
                new Person(0, "white hawk"),
                new Person(1, "Wściekły Kiwis"),
                new Person(2, "Aligator z Szafy")
        );

        expectedPeople.forEach(expectedPerson ->
                assertTrue(people.stream().anyMatch(p -> p.getName().equals(expectedPerson.getName())))
        );

        // Reward
        List<Reward> rewards = parsedData.getRewards();

        assertEquals(4, rewards.size());

        List<Reward> expectedRewards = Arrays.asList(
                new Reward(0, new RewardCategory(), "Marchewka lab", "+10% do lab"),
                new Reward(1, new RewardCategory(), "Lekarstwo", "odrobienie 2pkt lab"),
                new Reward(2, new RewardCategory(), "Rabat na sianko", "darmowa kartkówka"),
                new Reward(3, new RewardCategory(), "Weterynarz", "odrobienie 1 kartkówki"));

        expectedRewards.forEach(expectedReward ->
                assertTrue(rewards.stream().anyMatch(r -> r.getName().equals(expectedReward.getName()) &&
                        r.getDescription().equals(expectedReward.getDescription()))));

        // Quiz
        Quiz quiz = parsedData.getQuiz();

        assertEquals(2, quiz.getMaxScore());

        // Preference
        List<Preference> preferences = parsedData.getPreferences();

        assertEquals(people.size() * rewards.size(), preferences.size());

        List<Preference> expectedPreferences = Arrays.asList(
                new Preference(
                        0,
                        quiz,
                        new Person(0, "white hawk"),
                        new Reward(0, new RewardCategory(), "Marchewka lab", "+10% do lab"),
                        0),
                new Preference(
                        1,
                        quiz,
                        new Person(0, "white hawk"),
                        new Reward(0, new RewardCategory(), "Rabat na sianko", "darmowa kartkówka"),
                        1),
                new Preference(
                        2,
                        quiz,
                        new Person(0, "white hawk"),
                        new Reward(0, new RewardCategory(), "Lekarstwo", "odrobienie 2pkt lab"),
                        2),
                new Preference(
                        3,
                        quiz,
                        new Person(0, "white hawk"),
                        new Reward(0, new RewardCategory(), "Weterynarz", "odrobienie 1 kartkówki"),
                        3),
                new Preference(
                        0,
                        quiz,
                        new Person(0, "Wściekły Kiwis"),
                        new Reward(0, new RewardCategory(), "Marchewka lab", "+10% do lab"),
                        0),
                new Preference(
                        1,
                        quiz,
                        new Person(0, "Wściekły Kiwis"),
                        new Reward(0, new RewardCategory(), "Lekarstwo", "odrobienie 2pkt lab"),
                        1),
                new Preference(
                        2,
                        quiz,
                        new Person(0, "Wściekły Kiwis"),
                        new Reward(0, new RewardCategory(), "Weterynarz", "odrobienie 1 kartkówki"),
                        2),
                new Preference(
                        3,
                        quiz,
                        new Person(0, "Wściekły Kiwis"),
                        new Reward(0, new RewardCategory(), "Rabat na sianko", "darmowa kartkówka"),
                        3),
                new Preference(
                        0,
                        quiz,
                        new Person(0, "Aligator z Szafy"),
                        new Reward(0, new RewardCategory(), "Rabat na sianko", "darmowa kartkówka"),
                        0),
                new Preference(
                        1,
                        quiz,
                        new Person(0, "Aligator z Szafy"),
                        new Reward(0, new RewardCategory(), "Marchewka lab", "+10% do lab"),
                        1),
                new Preference(
                        2,
                        quiz,
                        new Person(0, "Aligator z Szafy"),
                        new Reward(0, new RewardCategory(), "Weterynarz", "odrobienie 1 kartkówki"),
                        2),
                new Preference(
                        3,
                        quiz,
                        new Person(0, "Aligator z Szafy"),
                        new Reward(0, new RewardCategory(), "Lekarstwo", "odrobienie 2pkt lab"),
                        3)
        );

        expectedPreferences.forEach(expectedPreference ->
                assertTrue(preferences.stream().anyMatch(p -> p.getPriority() == expectedPreference.getPriority() &&
                        p.getPerson().getName().equals(expectedPreference.getPerson().getName()) &&
                        p.getReward().getName().equals(expectedPreference.getReward().getName()) &&
                        p.getReward().getDescription().equals(expectedPreference.getReward().getDescription())))
        );

        // Result
        List<Result> results = parsedData.getResults();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        assertEquals(3, results.size());

        List<Result> expectedResults = Arrays.asList(
                new Result(0,
                        quiz,
                        new Person(0, "white hawk"),
                        dateFormat.parse("09.11.2023  19:34:54"),
                        dateFormat.parse("09.11.2023  19:39:38"),
                        1),
                new Result(1,
                        quiz,
                        new Person(1, "Wściekły Kiwis"),
                        dateFormat.parse("09.11.2023  19:35:08"),
                        dateFormat.parse("09.11.2023  19:39:42"),
                        1),
                new Result(2,
                        quiz,
                        new Person(2, "Aligator z Szafy"),
                        dateFormat.parse("09.11.2023  19:34:58"),
                        dateFormat.parse("09.11.2023  19:37:47"),
                        0)
        );

        expectedResults.forEach(expectedResult ->
                assertTrue(results.stream().anyMatch(r ->
                        r.getPerson().getName().equals(expectedResult.getPerson().getName()) &&
                                r.getStartDate().equals(expectedResult.getStartDate()) &&
                                r.getEndDate().equals(expectedResult.getEndDate()) &&
                                r.getScore() == expectedResult.getScore()))
        );
    }
}
