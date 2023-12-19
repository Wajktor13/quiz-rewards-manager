package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.repository.PreferenceRepository;
import majestatyczne.bestie.rewardsmanager.model.Person;
import majestatyczne.bestie.rewardsmanager.model.Preference;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;

    private final RewardService rewardService;

    private final PersonService personService;

    public void addPreference(Preference preference) {
        preferenceRepository.save(preference);
    }

    public void addPreferences(List<Preference> preferences) {
        preferences.forEach(preference -> {
            Reward reward = rewardService.findRewardByName(preference.getReward().getName()).orElse(null);
            Person person = personService.findPersonByName(preference.getPerson().getName()).orElse(null);

            if (reward != null && person != null) {
                preference.setReward(reward);
                preference.setPerson(person);
            }
            addPreference(preference);
        });
        preferenceRepository.saveAll(preferences);
    }
}
