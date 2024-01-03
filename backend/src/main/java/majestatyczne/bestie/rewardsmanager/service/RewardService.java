package majestatyczne.bestie.rewardsmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardDTO;
import majestatyczne.bestie.rewardsmanager.model.RewardCategory;
import majestatyczne.bestie.rewardsmanager.repository.RewardRepository;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RewardService {

    private final RewardRepository rewardRepository;

    private final RewardCategoryService rewardCategoryService;

    @Transactional
    public void addReward(Reward reward) {
        if (findRewardByName(reward.getName()).isEmpty()) {
            rewardRepository.save(reward);
        }
    }

    public Optional<Reward> findRewardByName(String name) {
        return Optional.ofNullable(rewardRepository.findRewardByName(name));
    }

    public List<Reward> findAllRewardsByName(List<String> names) {
        return rewardRepository.findAllRewardsByNames(names);
    }

    @Transactional
    public void addRewardsWithoutDuplicates(List<Reward> rewards) {
        List<String> names = rewards
                .stream()
                .map(Reward::getName)
                .toList();

        List<Reward> alreadyAddedRewards = findAllRewardsByName(names);

        List<Reward> newRewards = rewards
                .stream()
                .filter(reward -> alreadyAddedRewards
                        .stream()
                        .noneMatch(alreadyAddedReward -> alreadyAddedReward.getName().equals(reward.getName())))
                .toList();

        rewardRepository.saveAll(newRewards);
    }

    public List<RewardDTO> findAllRewards() {
        return rewardRepository
                .findAll()
                .stream()
                .map(reward -> new RewardDTO(reward.getId(), reward.getRewardCategory(), reward.getName(),
                        reward.getDescription()))
                .toList();
    }

    public Optional<Reward> findRewardById(int rewardId) {
        return rewardRepository.findById(rewardId);
    }

    @Transactional
    public void updateReward(Reward reward, RewardCategory rewardCategory, String name, String description) {
        reward.setRewardCategory(rewardCategory);
        reward.setName(name);
        reward.setDescription(description);

        rewardCategoryService.addReward(rewardCategory, reward);

        rewardRepository.save(reward);
    }

    @Transactional
    public boolean updateReward(RewardDTO rewardDTO) {
        return findRewardById(rewardDTO.getId())
                .map(reward -> {
                    updateReward(reward, rewardDTO.getRewardCategory(), rewardDTO.getName(), rewardDTO.getDescription());
                    return true;
                })
                .orElse(false);
    }
}
