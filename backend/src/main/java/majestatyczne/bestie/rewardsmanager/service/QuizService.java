package majestatyczne.bestie.rewardsmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.Result;
import majestatyczne.bestie.rewardsmanager.model.Preference;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategy;
import majestatyczne.bestie.rewardsmanager.repository.QuizRepository;
import majestatyczne.bestie.rewardsmanager.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    private final ResultService resultService;

    private final RewardStrategyService rewardStrategyService;

    private final PreferenceService preferenceService;

    @Transactional
    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public List<Quiz> findAllQuizzes() {
        return quizRepository
                .findAll();
    }

    public Optional<Quiz> findQuizById(int id) {
        return quizRepository.findById(id);
    }

    @Transactional
    public void deleteQuizById(int quizId) {
        preferenceService.deleteAllPreferencesByIds(
                preferenceService
                .findAllPreferencesByQuizId(quizId)
                .stream()
                .map(Preference::getId)
                .toList());

        resultService.deleteAllResultsByIds(
                resultService
                .findResultsByQuizId(quizId)
                .stream()
                .map(Result::getId)
                .toList());

        rewardStrategyService.deleteAllRewardStrategiesByIds(
                rewardStrategyService
                .findRewardStrategyByQuizId(quizId)
                .stream()
                .map(RewardStrategy::getId)
                .toList());

        quizRepository.deleteById(quizId);
    }
}
