package majestatyczne.bestie.rewardsmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.Person;
import majestatyczne.bestie.rewardsmanager.model.Quiz;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import majestatyczne.bestie.rewardsmanager.repository.ResultRepository;
import majestatyczne.bestie.rewardsmanager.model.Result;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;

    private final PersonService personService;

    public List<Result> findResultsByQuizId(int quizId) {
        return resultRepository.findResultsByQuizId(quizId);
    }

    @Transactional
    public void addResults(List<Result> results) {
        results.forEach(personService::updatePersonInResults);
        resultRepository.saveAll(results);
    }

    public Optional<Result> findResultById(int resultId) {
        return resultRepository.findById(resultId);
    }

    @Transactional
    public boolean updateResult(int resultId, Person person, Date startDate, Date endDate, int score, Reward reward) {
        return findResultById(resultId)
                .map(result -> updateResult(resultId, result.getQuiz(), person, startDate, endDate, score, reward))
                .orElse(false);
    }

    @Transactional
    public boolean updateResult(int resultId, Quiz quiz, Person person, Date startDate, Date endDate, int score,
                             Reward reward) {

        return findResultById(resultId)
                .map(result -> {
                    result.setPerson(person);
                    result.setQuiz(quiz);
                    result.setStartDate(startDate);
                    result.setEndDate(endDate);
                    result.setScore(score);
                    result.setReward(reward);

                    resultRepository.save(result);

                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public void deleteAllResultsByIds(List<Integer> resultIds) {
        resultRepository.deleteAllById(resultIds);
    }
}
