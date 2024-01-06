package majestatyczne.bestie.rewardsmanager.controller;

import jakarta.persistence.EntityNotFoundException;
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
        try {
            resultService.update(resultDTO.id(), resultDTO.person(), resultDTO.startDate(),
                    resultDTO.endDate(), resultDTO.score(), resultDTO.rewardDTO().id());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
