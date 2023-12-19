package majestatyczne.bestie.rewardsmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.repository.RewardRepository;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RewardService {

    private final RewardRepository rewardRepository;

    @Transactional
    public void addReward(Reward reward) {
        if (findRewardByName(reward.getName()).isEmpty()) {
            rewardRepository.save(reward);
        }
    }

    public Optional<Reward> findRewardByName(String name) {
        return Optional.ofNullable(rewardRepository.findRewardByName(name));
    }

    @Transactional
    public void addRewards(List<Reward> rewards) {
        List<Reward> newRewards = rewards
                .stream()
                .filter(reward -> findRewardByName(reward.getName()).isEmpty())
                .toList();
        rewardRepository.saveAll(newRewards);
    }
}
