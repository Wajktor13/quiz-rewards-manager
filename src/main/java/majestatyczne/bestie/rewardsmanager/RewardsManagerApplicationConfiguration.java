package majestatyczne.bestie.rewardsmanager;

import majestatyczne.bestie.rewardsmanager.util.IFileDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RewardsManagerApplicationConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(IFileDataLoader dataLoader) {
        return args -> {
            dataLoader.setInputFilePath("C:\\Users\\wajkt\\Desktop\\projects\\labs\\mi-pt-1300-majestatycznebestie\\src\\main\\resources\\example.xlsx");
            dataLoader.loadData();
        };
    }
}
