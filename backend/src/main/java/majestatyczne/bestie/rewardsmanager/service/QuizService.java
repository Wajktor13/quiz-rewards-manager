package majestatyczne.bestie.rewardsmanager.service;

import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dao.QuizRepository;
import majestatyczne.bestie.rewardsmanager.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public List<Quiz> findAllQuizzes() {
        return quizRepository.findAll();
    }
    public Optional<Quiz> findById(int id) {
        return quizRepository.findById(id);
    }
}
