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
    public boolean add(RewardCategory rewardCategory) {
        if (findByName(rewardCategory.getName()).isPresent()) {
            return false;
        }
        rewardCategoryRepository.save(rewardCategory);
        return true;
    }

    public List<RewardCategory> findAll() {
        return rewardCategoryRepository.findAll();
    }

    public Optional<RewardCategory> findById(int rewardCategoryId) {
        return rewardCategoryRepository.findById(rewardCategoryId);
    }

    @Transactional
    public boolean update(int rewardCategoryId, String name) {
        return findById(rewardCategoryId)
                .map(rewardCategory -> {
                    rewardCategory.setName(name);
                    rewardCategoryRepository.save(rewardCategory);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public void deleteById(int rewardCategoryId) {
        rewardCategoryRepository.deleteById(rewardCategoryId);
    }

    @Transactional
    public void addRewardToCategory(RewardCategory rewardCategory, Reward reward) {
        rewardCategory.getRewards().add(reward);

        rewardCategoryRepository.save(rewardCategory);
    }

    public Optional<RewardCategory> findByName(String name) {
        return Optional.ofNullable(rewardCategoryRepository.findByName(name));
    }

    @Transactional
    public void updateAll(List<RewardCategoryDTO> rewardCategoryDTOS) {
        List<RewardCategory> rewardCategories = rewardCategoryRepository.findAll();
        List<RewardCategory> updatedRewardCategories = rewardCategoryDTOS.stream()
                .map(rewardCategoryDTO -> {
                    RewardCategory matchingRewardCategory = rewardCategories
                            .stream()
                            .filter(reward -> reward.getId() == rewardCategoryDTO.id())
                            .findFirst()
                            .orElse(null);
                    matchingRewardCategory.setName(rewardCategoryDTO.name());
                    return matchingRewardCategory;
                })
                .toList();
        rewardCategoryRepository.saveAll(updatedRewardCategories);

    }
}
