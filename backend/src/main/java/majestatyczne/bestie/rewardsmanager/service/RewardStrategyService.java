package majestatyczne.bestie.rewardsmanager.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.Quiz;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategy;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategyParameter;
import majestatyczne.bestie.rewardsmanager.repository.RewardStrategyRepository;
import majestatyczne.bestie.rewardsmanager.reward_selection_strategy.RewardSelectionStrategy;
import majestatyczne.bestie.rewardsmanager.reward_selection_strategy.RewardStrategyType;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor_ ={@Lazy})
public class RewardStrategyService {

    private final RewardStrategyRepository rewardStrategyRepository;

    private final RewardStrategyParameterService rewardStrategyParameterService;

    @Lazy
    private final QuizService quizService;

    @Transactional
    public RewardStrategy add(int quizId, RewardStrategyType rewardStrategyType) {
        Optional<Quiz> quiz = quizService.findById(quizId);

        if (quiz.isEmpty()) {
            throw new EntityNotFoundException("quiz has not been found");
        }

        if (findByQuizId(quizId).isPresent()) {
            throw new EntityExistsException("strategy for the given quiz already exists");
        }

        RewardStrategy rewardStrategy = new RewardStrategy();
        rewardStrategy.setQuiz(quiz.get());
        rewardStrategy.setRewardStrategyType(rewardStrategyType);
        rewardStrategy.setParameters(new ArrayList<>());

        rewardStrategyRepository.save(rewardStrategy);

        return rewardStrategy;
    }

    @Transactional
    public RewardStrategy update(int quizId, RewardStrategyType rewardStrategyType) {
        Optional<RewardStrategy> rewardStrategyOptional = findByQuizId(quizId);
        if (rewardStrategyOptional.isEmpty()) {
            throw new EntityNotFoundException("reward strategy has not been found");
        }
        RewardStrategy rewardStrategy = rewardStrategyOptional.get();

        Optional<Quiz> quiz = quizService.findById(quizId);
        if (quiz.isEmpty()) {
            throw new EntityNotFoundException("quiz has not been found");
        }

        rewardStrategy.setRewardStrategyType(rewardStrategyType);
        rewardStrategy.setQuiz(quiz.get());

        rewardStrategyRepository.saveAndFlush(rewardStrategy);

        return rewardStrategy;
    }

    public Optional<RewardStrategy> findByQuizId(int quizId) {
        return Optional.ofNullable(rewardStrategyRepository.findRewardStrategyByQuizId(quizId));
    }

    public void deleteAllByIds(List<Integer> rewardStrategiesIds) {
        rewardStrategyParameterService.deleteAllByRewardStrategyIds(rewardStrategiesIds);
        rewardStrategyRepository.deleteAllById(rewardStrategiesIds);
    }

    @Transactional
    public void insertRewards(RewardStrategy rewardStrategy) {
        RewardSelectionStrategy rewardSelectionStrategy = RewardStrategyType.getRewardSelectionStrategy(rewardStrategy);
        rewardSelectionStrategy.insertRewards(rewardStrategy.getQuiz(), rewardStrategy, null);
    }

    @Transactional
    public void addParametersToStrategy(RewardStrategy rewardStrategy,
                                        List<RewardStrategyParameter> rewardStrategyParameters) {
        rewardStrategy.getParameters().addAll(rewardStrategyParameters);
        rewardStrategyRepository.saveAndFlush(rewardStrategy);
    }
}
