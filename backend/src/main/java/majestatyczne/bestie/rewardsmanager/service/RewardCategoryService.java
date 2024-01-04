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
    public boolean addRewardCategory(RewardCategoryDTO rewardCategoryDTO) {
        if (findRewardCategoryByName(rewardCategoryDTO.getName()).isEmpty()) {
            RewardCategory rewardCategory = new RewardCategory(
                    rewardCategoryDTO.getId(),
                    rewardCategoryDTO.getName(),
                    new ArrayList<>()
            );

            rewardCategoryRepository.save(rewardCategory);

            return true;
        }

        return false;
    }

    public List<RewardCategoryDTO> findAllRewardCategories() {
        return rewardCategoryRepository.findAll()
                .stream()
                .map(category -> new RewardCategoryDTO(category.getId(), category.getName()))
                .toList();
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
}
