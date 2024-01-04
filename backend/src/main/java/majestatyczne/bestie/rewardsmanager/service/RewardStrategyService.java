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
import java.util.Optional;

@Service
@AllArgsConstructor
public class RewardStrategyService {

    private final RewardStrategyRepository rewardStrategyRepository;

    private final RewardStrategyParameterService rewardStrategyParameterService;

    @Transactional
    public boolean addRewardStrategy(RewardStrategyDTO rewardStrategyDTO) {
        if (findRewardStrategyByQuizId(rewardStrategyDTO.getQuiz().getId()).isEmpty())
        {
            RewardStrategy rewardStrategy = new RewardStrategy();
            rewardStrategy.setRewardStrategyType(rewardStrategyDTO.getRewardStrategyType());
            rewardStrategy.setQuiz(rewardStrategyDTO.getQuiz());
            rewardStrategy.setParameters(new ArrayList<>());

            rewardStrategyRepository.save(rewardStrategy);

            List<RewardStrategyParameterDTO> rewardStrategyParameterDTOs = rewardStrategyDTO.getParameters();
            List<RewardStrategyParameter> rewardStrategyParameters = rewardStrategyParameterService
                    .addAllRewardStrategyParameters(rewardStrategyParameterDTOs, rewardStrategy);

            rewardStrategy.getParameters().addAll(rewardStrategyParameters);

            rewardStrategyRepository.save(rewardStrategy);

            return true;
        }

        return false;
    }

    @Transactional
    public boolean updateRewardStrategy(RewardStrategyDTO rewardStrategyDTO) {
        Optional<RewardStrategy> rewardStrategyOptional = rewardStrategyRepository.findById(rewardStrategyDTO.getId());

        if (rewardStrategyOptional.isPresent()) {
            RewardStrategy rewardStrategy = rewardStrategyOptional.get();
            rewardStrategy.setRewardStrategyType(rewardStrategyDTO.getRewardStrategyType());
            rewardStrategy.setQuiz(rewardStrategyDTO.getQuiz());

            rewardStrategyParameterService.updateAllRewardStrategyParameters(rewardStrategyDTO.getParameters(),
                    rewardStrategy);

            rewardStrategyRepository.save(rewardStrategy);

            return true;
        } else {
            return false;
        }
    }

    public Optional<RewardStrategy> findRewardStrategyByQuizId(int quizId) {
        return Optional.ofNullable(rewardStrategyRepository.findRewardStrategyByQuizId(quizId));
    }

    public void deleteAllRewardStrategiesByIds(List<Integer> rewardStrategiesIds) {
        rewardStrategyParameterService.deleteAllRewardStrategyParametersByRewardStrategyIds(rewardStrategiesIds);
        rewardStrategyRepository.deleteAllById(rewardStrategiesIds);
    }
}
