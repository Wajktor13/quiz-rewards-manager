package majestatyczne.bestie.rewardsmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardStrategyDTO;
import majestatyczne.bestie.rewardsmanager.dto.RewardStrategyParameterDTO;
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

    private final RewardStrategyParameterService rewardStrategyParameterService;

    @Transactional
    public void addRewardStrategy(RewardStrategyDTO rewardStrategyDTO) {
        RewardStrategy rewardStrategy = new RewardStrategy();
        rewardStrategy.setRewardStrategyType(rewardStrategyDTO.getRewardStrategyType());
        rewardStrategy.setQuiz(rewardStrategyDTO.getQuiz());
        rewardStrategy.setParameters(new ArrayList<>());

        rewardStrategyRepository.save(rewardStrategy);

        List<RewardStrategyParameterDTO> rewardStrategyParameterDTOs = rewardStrategyDTO.getParameters();
        List<RewardStrategyParameter> rewardStrategyParameters = rewardStrategyParameterService
                .addAllRewardStrategyParameters(rewardStrategyParameterDTOs, rewardStrategy);

        rewardStrategy.setParameters(rewardStrategyParameters);

        rewardStrategyRepository.save(rewardStrategy);
    }
}
