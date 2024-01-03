package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardStrategyDTO;
import majestatyczne.bestie.rewardsmanager.service.RewardStrategyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reward-strategy")
@RequiredArgsConstructor
public class RewardStrategyController {

    private final RewardStrategyService rewardStrategyService;

    @PostMapping
    public void addRewardStrategy(@RequestBody RewardStrategyDTO rewardStrategyDTO) {
        rewardStrategyService.addRewardStrategy(rewardStrategyDTO);
    }
}
