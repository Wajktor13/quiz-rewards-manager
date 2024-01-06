package majestatyczne.bestie.rewardsmanager.service;

import jakarta.persistence.EntityNotFoundException;
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
    public void add(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public List<Quiz> findAll() {
        return quizRepository
                .findAll();
    }

    public Quiz findById(int quizId) {
        return quizRepository
                .findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("quiz has not been found"));
    }

    @Transactional
    public void deleteById(int quizId) {
        findById(quizId); // throws error if quiz does not exist

        preferenceService.deleteAllByIds(
                preferenceService
                .findAllByQuizId(quizId)
                .stream()
                .map(Preference::getId)
                .toList());

        resultService.deleteAllByIds(
                resultService
                .findAllByQuizId(quizId)
                .stream()
                .map(Result::getId)
                .toList());

        rewardStrategyService.deleteAllByIds(
                rewardStrategyService
                .findByQuizId(quizId)
                .stream()
                .map(RewardStrategy::getId)
                .toList());

        quizRepository.deleteById(quizId);
    }
}
