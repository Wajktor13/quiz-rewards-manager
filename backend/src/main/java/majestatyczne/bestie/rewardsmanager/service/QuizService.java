package majestatyczne.bestie.rewardsmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.repository.QuizRepository;
import majestatyczne.bestie.rewardsmanager.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    @Transactional
    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public List<Quiz> findAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> findQuizById(int id) {
        return quizRepository.findById(id);
    }
}
