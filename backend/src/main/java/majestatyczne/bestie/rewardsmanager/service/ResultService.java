package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dao.ResultRepository;
import majestatyczne.bestie.rewardsmanager.model.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;
    private final PersonService personService;

    public void addResult(Result result) {
        resultRepository.save(result);
    }

    public List<Result> findResultsByQuizId(int quizId) {
        return resultRepository.findResultsByQuizId(quizId);
    }

    public void addResults(List<Result> results) {
        results.forEach(personService::updatePersonInResults);
        resultRepository.saveAll(results);
    }
}
