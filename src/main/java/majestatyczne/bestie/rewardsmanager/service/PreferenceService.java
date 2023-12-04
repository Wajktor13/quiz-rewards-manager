package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dao.PreferenceRepository;
import majestatyczne.bestie.rewardsmanager.model.Preference;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;

    public void addPreference(Preference preference) {
        this.preferenceRepository.save(preference);
    }
}
