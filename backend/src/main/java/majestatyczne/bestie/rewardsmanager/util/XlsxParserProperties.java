package majestatyczne.bestie.rewardsmanager.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "xlsx-parser")
public class XlsxParserProperties {

    private int startDateIndex;
    private int endDateIndex;
    private int scoreIndex;
    private int personNameIndex;
    private int rewardsIndex;
    private int notEvaluatedQuestions;
    private int columnsPerEvaluatedQuestion;
}
