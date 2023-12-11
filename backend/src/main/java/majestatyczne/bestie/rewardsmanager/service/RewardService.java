package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dao.RewardRepository;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RewardService {
    private final RewardRepository rewardRepository;

    public void addReward(Reward reward) {
        this.rewardRepository.save(reward);
    }
}
