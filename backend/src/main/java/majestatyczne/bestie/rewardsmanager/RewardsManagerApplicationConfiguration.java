package majestatyczne.bestie.rewardsmanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RewardsManagerApplicationConfiguration {
    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
        };
    }
}
