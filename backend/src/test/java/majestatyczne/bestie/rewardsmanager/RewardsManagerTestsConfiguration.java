package majestatyczne.bestie.rewardsmanager;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RewardsManagerTestsConfiguration {
    private final String validFilePath = "src/test/resources/valid_file.xlsx";

    public String getValidFilePath() {
        return validFilePath;
    }
}
