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
    public List<ResultDTO> getAllByQuizId(@RequestParam int quizId) {
        return resultService
                .findAllByQuizId(quizId)
                .stream()
                .map(ResultDTO::convertToDTO)
                .toList();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ResultDTO resultDTO) {
        return resultService.update(resultDTO.id(), resultDTO.person(), resultDTO.startDate(),
                resultDTO.endDate(), resultDTO.score(), resultDTO.rewardDTO().id())
                ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
