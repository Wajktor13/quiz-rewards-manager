package majestatyczne.bestie.rewardsmanager;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RewardsManagerTestsConfiguration {

    private final String fullValidFilePath = "src/test/resources/full_valid_file.xlsx";

    private final String shortValidFile1Path = "src/test/resources/short_valid_file1.xlsx";

    private final String shortValidFile2Path = "src/test/resources/short_valid_file2.xlsx";

    public String getFullValidFilePath() {
        return fullValidFilePath;
    }

    public String getShortValidFile1Path() {
        return shortValidFile1Path;
    }

    public String getShortValidFile2Path() {
        return shortValidFile2Path;
    }
}
