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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RewardService {

    private final RewardRepository rewardRepository;

    private final RewardCategoryService rewardCategoryService;

    @Transactional
    public boolean addReward(Reward reward) {
        if (findRewardByName(reward.getName()).isEmpty()) {
            rewardRepository.save(reward);
            return true;
        }
        return false;
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
                .map(reward -> new RewardDTO(reward.getId(), RewardCategoryDTO.fromRewardCategory(reward.getRewardCategory()), reward.getName(),
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

        if (reward.getRewardCategory() != null) {
            rewardCategoryService.addReward(rewardCategory, reward);
        }

        rewardRepository.save(reward);
    }

    public boolean updateReward(int rewardId, RewardCategory rewardCategory, String name, String description) {
        return findRewardById(rewardId)
                .map(reward -> {
                    updateReward(reward, rewardCategory, name, description);
                    return true;
                })
                .orElse(false);
    }
    public void updateRewards(List<RewardDTO> rewardDTOS) {
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
                        matchingReward.setRewardCategory(rewardCategoryService.findRewardCategoryById(rewardDTO.getRewardCategory().getId()).orElse(null));
                    }
                    matchingReward.setDescription(rewardDTO.getDescription());
                    return matchingReward;
                })
                .toList();
        rewardRepository.saveAll(updatedRewards);

    }
//
//    @Transactional
//    public boolean updateReward(RewardDTO rewardDTO) {
//        return findRewardById(rewardDTO.getId())
//                .map(reward -> {
//                    if (rewardDTO.getRewardCategory() == null) {
//                        updateReward(reward, null, rewardDTO.getName(), rewardDTO.getDescription());
//                    } else {
//                        updateReward(reward, rewardCategoryService.findRewardCategoryById(rewardDTO.getRewardCategory().getId()).orElse(null), rewardDTO.getName(), rewardDTO.getDescription());
//                    }
//
//                    return true;
//                })
//                .orElse(false);
//    }

    public void deleteRewardById(int rewardId) {
        rewardRepository.deleteById(rewardId);
    }
}
