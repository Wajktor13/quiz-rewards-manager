package majestatyczne.bestie.rewardsmanager.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.QuizDTO;
import majestatyczne.bestie.rewardsmanager.model.Quiz;
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
    public List<QuizDTO> getAll() {
        return quizService
                .findAll()
                .stream()
                .map(QuizDTO::convertToDTO)
                .toList();
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<?> getById(@PathVariable int quizId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(QuizDTO.convertToDTO(quizService.findById(quizId)));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<String> deleteById(@PathVariable int quizId) {
        try {
            quizService.deleteById(quizId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
