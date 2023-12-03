package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dao.RewardRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RewardService {
    private final RewardRepository rewardRepository;
}
