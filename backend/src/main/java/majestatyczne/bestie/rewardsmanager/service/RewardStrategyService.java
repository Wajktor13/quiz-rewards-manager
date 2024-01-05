package majestatyczne.bestie.rewardsmanager.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.QuizDTO;
import majestatyczne.bestie.rewardsmanager.dto.RewardStrategyDTO;
import majestatyczne.bestie.rewardsmanager.dto.RewardStrategyParameterDTO;
import majestatyczne.bestie.rewardsmanager.model.Quiz;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategy;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategyParameter;
import majestatyczne.bestie.rewardsmanager.repository.RewardStrategyRepository;
import majestatyczne.bestie.rewardsmanager.reward_selection_strategy.RewardSelectionStrategy;
import majestatyczne.bestie.rewardsmanager.reward_selection_strategy.RewardStrategyType;
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
    public boolean addRewardStrategy(RewardStrategyDTO rewardStrategyDTO, Quiz quiz) {
        if (findRewardStrategyByQuizId(rewardStrategyDTO.getQuiz().getId()).isPresent()) {
            return false;
        }

        RewardStrategy rewardStrategy = new RewardStrategy();
        rewardStrategy.setRewardStrategyType(rewardStrategyDTO.getRewardStrategyType());
        rewardStrategy.setParameters(new ArrayList<>());
        rewardStrategy.setQuiz(quiz);

        rewardStrategyRepository.save(rewardStrategy);

        // update parameters
        List<RewardStrategyParameterDTO> rewardStrategyParameterDTOs = rewardStrategyDTO.getParameters();
        List<RewardStrategyParameter> rewardStrategyParameters = rewardStrategyParameterService
                .addAllRewardStrategyParameters(rewardStrategyParameterDTOs, rewardStrategy);

        rewardStrategy.getParameters().addAll(rewardStrategyParameters);

        rewardStrategyRepository.save(rewardStrategy);

        insertRewards(rewardStrategy);

        return true;
    }

    @Transactional
    public boolean updateRewardStrategy(RewardStrategyDTO rewardStrategyDTO, Quiz quiz) {
        Optional<RewardStrategy> rewardStrategyOptional = rewardStrategyRepository.findById(rewardStrategyDTO.getId());

        if (rewardStrategyOptional.isPresent()) {
            RewardStrategy rewardStrategy = rewardStrategyOptional.get();
            rewardStrategy.setRewardStrategyType(rewardStrategyDTO.getRewardStrategyType());
            rewardStrategy.setQuiz(quiz);

            rewardStrategyParameterService.updateAllRewardStrategyParameters(rewardStrategyDTO.getParameters(),
                    rewardStrategy);

            rewardStrategyRepository.saveAndFlush(rewardStrategy);

            insertRewards(rewardStrategy);

            return true;
        }
        return false;
    }

    public Optional<RewardStrategy> findRewardStrategyByQuizId(int quizId) {
        return Optional.ofNullable(rewardStrategyRepository.findRewardStrategyByQuizId(quizId));
    }

    public void deleteAllRewardStrategiesByIds(List<Integer> rewardStrategiesIds) {
        rewardStrategyParameterService.deleteAllRewardStrategyParametersByRewardStrategyIds(rewardStrategiesIds);
        rewardStrategyRepository.deleteAllById(rewardStrategiesIds);
    }

    @Transactional
    public void insertRewards(RewardStrategy rewardStrategy) {
        RewardSelectionStrategy rewardSelectionStrategy = RewardStrategyType.getRewardSelectionStrategy(rewardStrategy);
        rewardSelectionStrategy.insertRewards(rewardStrategy.getQuiz(), rewardStrategy, null);
    }
}
