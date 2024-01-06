package majestatyczne.bestie.rewardsmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategy;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategyParameter;
import majestatyczne.bestie.rewardsmanager.repository.RewardStrategyParameterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RewardStrategyParameterService {

    private final RewardStrategyParameterRepository rewardStrategyParameterRepository;


    @Transactional
    public void addAll(List<RewardStrategyParameter> rewardStrategyParameters) {
        rewardStrategyParameterRepository.saveAllAndFlush(rewardStrategyParameters);
    }

    @Transactional
    public void updateAll(List<RewardStrategyParameter> rewardStrategyParameters,
                          RewardStrategy rewardStrategy) {
        rewardStrategyParameterRepository.deleteAllByRewardStrategyId(rewardStrategy.getId());
        addAll(rewardStrategyParameters);
    }

    @Transactional
    public void deleteAllByRewardStrategyIds(List<Integer> rewardStrategyIds) {
        rewardStrategyParameterRepository.deleteAllByRewardStrategyIds(rewardStrategyIds);
    }
}
