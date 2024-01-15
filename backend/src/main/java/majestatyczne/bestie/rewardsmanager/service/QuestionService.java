package majestatyczne.bestie.rewardsmanager.service;

import majestatyczne.bestie.rewardsmanager.repository.AnswerRepository;
import majestatyczne.bestie.rewardsmanager.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RqService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
}
