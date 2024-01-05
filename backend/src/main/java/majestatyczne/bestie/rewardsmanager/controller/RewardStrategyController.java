package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardStrategyDTO;
import majestatyczne.bestie.rewardsmanager.model.Quiz;
import majestatyczne.bestie.rewardsmanager.service.QuizService;
import majestatyczne.bestie.rewardsmanager.service.RewardStrategyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reward-strategies")
@RequiredArgsConstructor
public class RewardStrategyController {

    private final RewardStrategyService rewardStrategyService;

    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<String> addRewardStrategy(@RequestBody RewardStrategyDTO rewardStrategyDTO) {
        rewardStrategyDTO.setQuiz(rewardStrategyDTO.getQuiz());

        Quiz quiz = quizService.findQuizById(rewardStrategyDTO.getQuiz().getId()).orElse(null);

        return rewardStrategyService.addRewardStrategy(rewardStrategyDTO, quiz) ?
                ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.CONFLICT).body(
                        "Strategy for the given quiz already exists");
    }

    @PutMapping
    public ResponseEntity<?> updateRewardStrategy(@RequestBody RewardStrategyDTO rewardStrategyDTO) {
        rewardStrategyDTO.setQuiz(rewardStrategyDTO.getQuiz());

        Quiz quiz = quizService.findQuizById(rewardStrategyDTO.getQuiz().getId()).orElse(null);

        return rewardStrategyService.updateRewardStrategy(rewardStrategyDTO, quiz) ?
                ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("/{quizId}")
    public ResponseEntity<RewardStrategyDTO> getRewardStrategyByQuizId(@PathVariable int quizId) {
        var rewardStrategy = rewardStrategyService.findRewardStrategyByQuizId(quizId);
        return rewardStrategy.map(value -> ResponseEntity.status(HttpStatus.OK)
                        .body(RewardStrategyDTO.fromRewardStrategy(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
