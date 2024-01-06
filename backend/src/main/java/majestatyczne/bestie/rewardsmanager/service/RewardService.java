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
    public boolean add(Reward reward) {
        if (findByName(reward.getName()).isEmpty()) {
            rewardRepository.save(reward);
            return true;
        }
        return false;
    }

    public Optional<Reward> findByName(String name) {
        return Optional.ofNullable(rewardRepository.findByName(name));
    }

    public List<Reward> findAllByNames(List<String> names) {
        return rewardRepository.findAllByNames(names);
    }

    @Transactional
    public void addWithoutDuplicates(List<Reward> rewards) {
        List<String> names = rewards
                .stream()
                .map(Reward::getName)
                .toList();

        List<Reward> alreadyAddedRewards = findAllByNames(names);

        List<Reward> newRewards = rewards
                .stream()
                .filter(reward -> alreadyAddedRewards
                        .stream()
                        .noneMatch(alreadyAddedReward -> alreadyAddedReward.getName().equals(reward.getName())))
                .toList();

        rewardRepository.saveAll(newRewards);
    }

    public List<Reward> findAll() {
        return rewardRepository.findAll();
    }

    public Optional<Reward> findById(int rewardId) {
        return rewardRepository.findById(rewardId);
    }

    @Transactional
    public void update(Reward reward, RewardCategory rewardCategory, String name, String description) {
        reward.setRewardCategory(rewardCategory);
        reward.setName(name);
        reward.setDescription(description);

        if (reward.getRewardCategory() != null) {
            rewardCategoryService.addRewardToCategory(rewardCategory, reward);
        }

        rewardRepository.save(reward);
    }

    @Transactional
    public boolean update(int rewardId, RewardCategory rewardCategory, String name, String description) {
        return findById(rewardId)
                .map(reward -> {
                    update(reward, rewardCategory, name, description);
                    return true;
                })
                .orElse(false);
    }

    public void updateAll(List<RewardDTO> rewardDTOS) {
        List<Reward> rewards = rewardRepository.findAll();
        List<Reward> updatedRewards = rewardDTOS.stream()
                .map(rewardDTO -> {
                    Reward matchingReward = rewards
                            .stream()
                            .filter(reward -> reward.getId() == rewardDTO.id())
                            .findFirst()
                            .orElse(null);
                    matchingReward.setName(rewardDTO.name());
                    if (rewardDTO.rewardCategoryDTO() == null) {
                        matchingReward.setRewardCategory(null);
                    } else {
                        matchingReward.setRewardCategory(rewardCategoryService.findById(rewardDTO.rewardCategoryDTO().id()).orElse(null));
                    }
                    matchingReward.setDescription(rewardDTO.description());
                    return matchingReward;
                })
                .toList();
        rewardRepository.saveAll(updatedRewards);

    }

    public void deleteById(int rewardId) {
        rewardRepository.deleteById(rewardId);
    }
}
