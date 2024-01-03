package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardStrategyDTO;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategy;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategyParameter;
import majestatyczne.bestie.rewardsmanager.repository.RewardStrategyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RewardStrategyService {

    private final RewardStrategyRepository rewardStrategyRepository;

    public void addRewardStrategyFromDTO(RewardStrategyDTO rewardStrategyDTO) {
        RewardStrategy rewardStrategy = new RewardStrategy();
        rewardStrategy.setRewardStrategyType(rewardStrategyDTO.getRewardStrategyType());
        rewardStrategy.setQuiz(rewardStrategyDTO.getQuiz());
        rewardStrategy.setParameters(new ArrayList<>()); // needs to be fetched from RewardStrategyParameterService

        rewardStrategyRepository.save(rewardStrategy);
    }
}
