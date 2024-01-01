package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.QuizDTO;
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
@RequestMapping("quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping()
    public List<QuizDTO> getAllQuizzes() {
        return quizService.findAllQuizzes()
                .stream()
                .map(quiz -> new QuizDTO(quiz.getId(), quiz.getName(), quiz.getMaxScore(), quiz.getDate()))
                .toList();
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<?> getQuizById(@PathVariable int quizId) {
        Optional<Quiz> quiz = quizService.findQuizById(quizId);

        return quiz.map(q -> ResponseEntity.ok(new QuizDTO(q.getId(), q.getName(), q.getMaxScore(), q.getDate())))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
