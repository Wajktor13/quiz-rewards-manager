package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dao.RewardRepository;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RewardService {
    private final RewardRepository rewardRepository;

    public void addReward(Reward reward) {
        if (findRewardByName(reward.getName()).isEmpty()) {
            rewardRepository.save(reward);
        }
    }

    public Optional<Reward> findRewardByName(String name) {
        return Optional.ofNullable(rewardRepository.findRewardByName(name));
    }
}
