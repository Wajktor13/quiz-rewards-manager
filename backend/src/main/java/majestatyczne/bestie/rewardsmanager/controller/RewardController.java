package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardDTO;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import majestatyczne.bestie.rewardsmanager.service.RewardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @GetMapping
    public List<RewardDTO> getAllRewards() {
        return rewardService.findAllRewards()
                .stream()
                .map(reward -> new RewardDTO(reward.getId(), reward.getRewardCategory(), reward.getName(),
                        reward.getDescription()))
                .toList();
    }

    @PutMapping
    public ResponseEntity<?> updateReward(@RequestBody RewardDTO rewardDTO) {
        return rewardService.updateReward(rewardDTO) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
