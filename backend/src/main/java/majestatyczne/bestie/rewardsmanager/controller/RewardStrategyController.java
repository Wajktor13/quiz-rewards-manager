package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardStrategyDTO;
import majestatyczne.bestie.rewardsmanager.service.RewardStrategyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reward-strategies")
@RequiredArgsConstructor
public class RewardStrategyController {

    private final RewardStrategyService rewardStrategyService;

    @PostMapping
    public ResponseEntity<String> addRewardStrategy(@RequestBody RewardStrategyDTO rewardStrategyDTO) {
        return rewardStrategyService.addRewardStrategy(rewardStrategyDTO) ?
                ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.CONFLICT).body(
                        "Strategy for the given quiz already exists");
    }

    @PutMapping
    public ResponseEntity<?> updateRewardStrategy(@RequestBody RewardStrategyDTO rewardStrategyDTO) {
        return rewardStrategyService.updateRewardStrategy(rewardStrategyDTO) ?
                ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
