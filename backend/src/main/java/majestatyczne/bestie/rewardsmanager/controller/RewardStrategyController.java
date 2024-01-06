package majestatyczne.bestie.rewardsmanager.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardStrategyDTO;
import majestatyczne.bestie.rewardsmanager.dto.RewardStrategyParameterDTO;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategy;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategyParameter;
import majestatyczne.bestie.rewardsmanager.service.RewardCategoryService;
import majestatyczne.bestie.rewardsmanager.service.RewardStrategyParameterService;
import majestatyczne.bestie.rewardsmanager.service.RewardStrategyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("reward-strategies")
@RequiredArgsConstructor
public class RewardStrategyController {

    private final RewardStrategyService rewardStrategyService;

    private final RewardStrategyParameterService rewardStrategyParameterService;

    private final RewardCategoryService rewardCategoryService;

    @PostMapping
    public ResponseEntity<String> addWithParameters(@RequestBody RewardStrategyDTO rewardStrategyDTO) {
        try {
            RewardStrategy rewardStrategy = rewardStrategyService.add(rewardStrategyDTO.getQuizDTO().id(),
                    rewardStrategyDTO.getRewardStrategyType());

            List<RewardStrategyParameter> rewardStrategyParameters =
                    RewardStrategyParameterDTO.convertAllFromDTO(rewardStrategyDTO.getRewardStrategyParameterDTOs(), rewardStrategy,
                            rewardCategoryService);

            rewardStrategyParameterService.addAll(rewardStrategyParameters);

            rewardStrategyService.addParametersToStrategy(rewardStrategy, rewardStrategyParameters);

            // should it be called here or by a different endpoint?
            rewardStrategyService.insertRewards(rewardStrategy);

            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> updateWithParameters(@RequestBody RewardStrategyDTO rewardStrategyDTO) {
        try {
            RewardStrategy rewardStrategy = rewardStrategyService.update(rewardStrategyDTO.getQuizDTO().id(),
                    rewardStrategyDTO.getRewardStrategyType());

            List<RewardStrategyParameter> rewardStrategyParameters =
                    RewardStrategyParameterDTO.convertAllFromDTO(rewardStrategyDTO.getRewardStrategyParameterDTOs(), rewardStrategy,
                            rewardCategoryService);

            rewardStrategyParameterService.updateAll(rewardStrategyParameters, rewardStrategy);

            rewardStrategyService.addParametersToStrategy(rewardStrategy, rewardStrategyParameters);

            // should it be called here or by a different endpoint?
            rewardStrategyService.insertRewards(rewardStrategy);

            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<RewardStrategyDTO> getByQuizId(@PathVariable int quizId) {
        Optional<RewardStrategy> rewardStrategy = rewardStrategyService.findByQuizId(quizId);

        return rewardStrategy
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(RewardStrategyDTO.convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
