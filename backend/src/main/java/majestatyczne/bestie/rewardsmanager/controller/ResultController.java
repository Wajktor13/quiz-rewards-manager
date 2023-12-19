package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.ResultDTO;
import majestatyczne.bestie.rewardsmanager.model.Result;
import majestatyczne.bestie.rewardsmanager.service.ResultService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/by_quiz_id/{quizId}")
    public List<ResultDTO> getResultsByQuizId(@PathVariable int quizId) {
        return resultService.findResultsByQuizId(quizId)
                .stream()
                .map(result -> new ResultDTO(result.getId(), result.getPerson(), result.getStartDate(),
                        result.getEndDate(), result.getScore()))
                .toList();
    }
}
