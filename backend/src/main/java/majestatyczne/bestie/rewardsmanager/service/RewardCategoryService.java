package majestatyczne.bestie.rewardsmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardCategoryDTO;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import majestatyczne.bestie.rewardsmanager.repository.RewardCategoryRepository;
import majestatyczne.bestie.rewardsmanager.model.RewardCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RewardCategoryService {

    private final RewardCategoryRepository rewardCategoryRepository;

    @Transactional
    public void addRewardCategory(RewardCategoryDTO rewardCategoryDTO) {
        RewardCategory rewardCategory = new RewardCategory(
                rewardCategoryDTO.getId(),
                rewardCategoryDTO.getName(),
                new ArrayList<>()
        );

        rewardCategoryRepository.save(rewardCategory);
    }

    public List<RewardCategory> findAllRewardCategories() {
        return rewardCategoryRepository.findAll();
    }

    public Optional<RewardCategory> findRewardCategoryById(int rewardCategoryId) {
        return rewardCategoryRepository.findById(rewardCategoryId);
    }

    @Transactional
    public void updateRewardCategory(RewardCategory rewardCategory, String name) {
        rewardCategory.setName(name);

        rewardCategoryRepository.save(rewardCategory);
    }

    @Transactional
    public boolean updateRewardCategory(RewardCategoryDTO rewardCategoryDTO) {
        return findRewardCategoryById(rewardCategoryDTO.getId())
                .map(rewardCategory -> {
                    updateRewardCategory(rewardCategory, rewardCategoryDTO.getName());
                    return true;
                }).orElse(false);
    }

    @Transactional
    public void deleteRewardCategory(RewardCategoryDTO rewardCategoryDTO) {
        rewardCategoryRepository.deleteById(rewardCategoryDTO.getId());
    }

    @Transactional
    public void addReward(RewardCategory rewardCategory, Reward reward) {
        rewardCategory.getRewards().add(reward);

        rewardCategoryRepository.save(rewardCategory);
    }
}
