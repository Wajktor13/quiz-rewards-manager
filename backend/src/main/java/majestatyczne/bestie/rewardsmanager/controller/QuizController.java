package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.QuizDTO;
import majestatyczne.bestie.rewardsmanager.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public List<QuizDTO> getAllQuizzes() {
        return quizService.findAllQuizzes().stream()
                .map(quiz -> new QuizDTO(quiz.getId(), quiz.getName(), quiz.getMaxScore(), quiz.getDate()))
                .toList();
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<?> getQuizById(@PathVariable int quizId) {
        return quizService
                .findQuizById(quizId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<?> deleteQuizById(@PathVariable int quizId) {
        return quizService.findQuizById(quizId)
                .map(quiz -> {
                    quizService.deleteQuizById(quizId);
                    return ResponseEntity.status(HttpStatus.OK).build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
