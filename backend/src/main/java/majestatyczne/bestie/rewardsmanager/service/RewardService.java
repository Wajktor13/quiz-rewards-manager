package majestatyczne.bestie.rewardsmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardCategoryDTO;
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

    public List<RewardDTO> findAll() {
        return rewardRepository
                .findAll()
                .stream()
                .map(reward -> new RewardDTO(reward.getId(), RewardCategoryDTO.convertToDTO(reward.getRewardCategory()), reward.getName(),
                        reward.getDescription()))
                .toList();
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
                            .filter(reward -> reward.getId() == rewardDTO.getId())
                            .findFirst()
                            .orElse(null);
                    matchingReward.setName(rewardDTO.getName());
                    if (rewardDTO.getRewardCategory() == null) {
                        matchingReward.setRewardCategory(null);
                    } else {
                        matchingReward.setRewardCategory(rewardCategoryService.findById(rewardDTO.getRewardCategory().getId()).orElse(null));
                    }
                    matchingReward.setDescription(rewardDTO.getDescription());
                    return matchingReward;
                })
                .toList();
        rewardRepository.saveAll(updatedRewards);

    }

    public void deleteById(int rewardId) {
        rewardRepository.deleteById(rewardId);
    }
}
