package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardStrategyDTO;
import majestatyczne.bestie.rewardsmanager.service.RewardStrategyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reward-strategy")
@RequiredArgsConstructor
public class RewardStrategyController {

    private final RewardStrategyService rewardStrategyService;

    @PostMapping
    public void addRewardStrategy(@RequestBody RewardStrategyDTO rewardStrategyDTO) {
        rewardStrategyService.addRewardStrategy(rewardStrategyDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateRewardStrategy(@RequestBody RewardStrategyDTO rewardStrategyDTO) {
        return rewardStrategyService.updateRewardStrategy(rewardStrategyDTO) ?
                ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
