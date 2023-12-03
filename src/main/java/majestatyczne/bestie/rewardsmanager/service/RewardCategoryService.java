package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dao.RewardCategoryRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RewardCategoryService {
    private final RewardCategoryRepository rewardCategoryRepository;
}
