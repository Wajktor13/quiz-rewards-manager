package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.Quiz;
import majestatyczne.bestie.rewardsmanager.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/all")
    public List<Quiz> getAllQuizzes() {
        return quizService.findAllQuizzes();
    }
    @GetMapping("/{quizId}")
    public ResponseEntity<?> getQuizById(@PathVariable int quizId) {
        Optional<Quiz> quiz = quizService.findById(quizId);
        return quiz.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }
}
