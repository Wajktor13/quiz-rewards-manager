package majestatyczne.bestie.rewardsmanager.service;

import jakarta.transaction.Transactional;
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

    @Transactional
    public void add(Preference preference) {
        preferenceRepository.save(preference);
    }

    @Transactional
    public void addAll(List<Preference> preferences) {
        preferences.forEach(preference -> {
            Reward reward = rewardService.findByName(preference.getReward().getName()).orElse(null);
            Person person = personService.findByName(preference.getPerson().getName()).orElse(null);

            if (reward != null && person != null) {
                preference.setReward(reward);
                preference.setPerson(person);
            }
            add(preference);
        });
        preferenceRepository.saveAll(preferences);
    }

    public List<Preference> findAllByQuizId(int quizId) {
        return preferenceRepository.findAllByQuizId(quizId);
    }

    @Transactional
    public void deleteAllByIds(List<Integer> PreferenceIds) {
        preferenceRepository.deleteAllById(PreferenceIds);
    }
}
