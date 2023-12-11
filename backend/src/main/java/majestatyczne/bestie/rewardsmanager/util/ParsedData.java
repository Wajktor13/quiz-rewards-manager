package majestatyczne.bestie.rewardsmanager.util;

import lombok.Getter;
import lombok.Setter;
import majestatyczne.bestie.rewardsmanager.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class ParsedData {

    private Quiz quiz = new Quiz();
    private List<Person> people = new ArrayList<>();
    private List<Preference> preferences = new ArrayList<>();
    private List<Result> results = new ArrayList<>();
    private List<Reward> rewards = new ArrayList<>();
}
