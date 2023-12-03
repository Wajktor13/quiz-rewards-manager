package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dao.PreferenceRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;
}
