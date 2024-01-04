package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.ResultDTO;
import majestatyczne.bestie.rewardsmanager.service.ResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping
    public List<ResultDTO> getResultsByQuizId(@RequestParam int quizId) {
        return resultService
                .findResultsByQuizId(quizId)
                .stream()
                .map(result -> new ResultDTO(result.getId(), result.getPerson(), result.getStartDate(),
                        result.getEndDate(), result.getScore(), result.getReward()))
                .toList();
    }

    @PutMapping
    public ResponseEntity<?> updateResult(@RequestBody ResultDTO resultDTO) {
        return resultService.updateResult(resultDTO.getId(), resultDTO.getPerson(), resultDTO.getStartDate(),
                resultDTO.getEndDate(), resultDTO.getScore(), resultDTO.getReward())
                ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
