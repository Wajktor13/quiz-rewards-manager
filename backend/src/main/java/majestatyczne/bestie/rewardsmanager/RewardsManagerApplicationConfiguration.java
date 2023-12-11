package majestatyczne.bestie.rewardsmanager;

import majestatyczne.bestie.rewardsmanager.util.FileDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RewardsManagerApplicationConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(FileDataLoader dataLoader) {
        return args -> {
            dataLoader.setInputFilePath("resources/example.xlsx");
//            dataLoader.loadData();
        };
    }
}
