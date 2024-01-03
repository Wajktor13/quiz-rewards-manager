package majestatyczne.bestie.rewardsmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
    public void addRewardCategory(RewardCategory rewardCategory) {
        rewardCategoryRepository.save(rewardCategory);
    }

    public List<RewardCategory> findAllRewardCategories() {
        return rewardCategoryRepository.findAll();
    }

    public Optional<RewardCategory> findRewardCategoryById(int rewardCategoryId) {
        return rewardCategoryRepository.findById(rewardCategoryId);
    }
}
