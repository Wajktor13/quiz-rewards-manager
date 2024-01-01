package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.ResultDTO;
import majestatyczne.bestie.rewardsmanager.model.Result;
import majestatyczne.bestie.rewardsmanager.service.ResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping
    public List<ResultDTO> getResultsByQuizId(@RequestParam int quizId) {
        return resultService.findResultsByQuizId(quizId)
                .stream()
                .map(result -> new ResultDTO(result.getId(), result.getPerson(), result.getStartDate(),
                        result.getEndDate(), result.getScore(), result.getReward()))
                .toList();
    }

    @PutMapping
    public ResponseEntity<?> updateResult(@RequestBody ResultDTO resultDTO) {
        Optional<Result> result = resultService.findResultById(resultDTO.getId());

        if (result.isPresent()) {
            // Quiz does not change
            resultService.updateResult(result.get(), result.get().getQuiz(), resultDTO.getPerson(),
                    resultDTO.getStartDate(), resultDTO.getEndDate(), resultDTO.getScore(), resultDTO.getReward());

            return ResponseEntity.status(HttpStatus.OK).build();

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
