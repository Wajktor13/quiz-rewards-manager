package majestatyczne.bestie.rewardsmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardCategoryDTO;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import majestatyczne.bestie.rewardsmanager.repository.RewardCategoryRepository;
import majestatyczne.bestie.rewardsmanager.model.RewardCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RewardCategoryService {

    private final RewardCategoryRepository rewardCategoryRepository;

    @Transactional
    public boolean addRewardCategory(RewardCategory rewardCategory) {
        if (findRewardCategoryByName(rewardCategory.getName()).isPresent()) {
            return false;
        }
        rewardCategoryRepository.save(rewardCategory);
        return true;
    }

    public List<RewardCategory> findAllRewardCategories() {
        return rewardCategoryRepository.findAll();
    }

    public Optional<RewardCategory> findRewardCategoryById(int rewardCategoryId) {
        return rewardCategoryRepository.findById(rewardCategoryId);
    }

    @Transactional
    public boolean updateRewardCategory(int rewardCategoryId, String name) {
        return findRewardCategoryById(rewardCategoryId)
                .map(rewardCategory -> {
                    rewardCategory.setName(name);
                    rewardCategoryRepository.save(rewardCategory);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public void deleteRewardCategoryById(int rewardCategoryId) {
        rewardCategoryRepository.deleteById(rewardCategoryId);
    }

    @Transactional
    public void addReward(RewardCategory rewardCategory, Reward reward) {
        rewardCategory.getRewards().add(reward);

        rewardCategoryRepository.save(rewardCategory);
    }

    public Optional<RewardCategory> findRewardCategoryByName(String name) {
        return Optional.ofNullable(rewardCategoryRepository.findRewardCategoryByName(name));
    }

    @Transactional
    public void updateRewardCategories(List<RewardCategoryDTO> rewardCategoryDTOS) {
        List<RewardCategory> rewardCategories = rewardCategoryRepository.findAll();
        List<RewardCategory> updatedRewardCategories = rewardCategoryDTOS.stream()
                .map(rewardCategoryDTO -> {
                    RewardCategory matchingRewardCategory = rewardCategories
                            .stream()
                            .filter(reward -> reward.getId() == rewardCategoryDTO.getId())
                            .findFirst()
                            .orElse(null);
                    matchingRewardCategory.setName(rewardCategoryDTO.getName());
                    return matchingRewardCategory;
                })
                .toList();
        rewardCategoryRepository.saveAll(updatedRewardCategories);

    }
}
