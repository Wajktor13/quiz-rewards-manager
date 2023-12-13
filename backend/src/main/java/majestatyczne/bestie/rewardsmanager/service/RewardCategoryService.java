package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dao.RewardCategoryRepository;
import majestatyczne.bestie.rewardsmanager.model.RewardCategory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RewardCategoryService {
    private final RewardCategoryRepository rewardCategoryRepository;

    private void addRewardCategory(RewardCategory rewardCategory) {
        rewardCategoryRepository.save(rewardCategory);
    }
}
