package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardDTO;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import majestatyczne.bestie.rewardsmanager.service.RewardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @GetMapping
    public List<RewardDTO> getAllRewards() {
        return rewardService.findAllRewards();
    }

    @PutMapping
    public ResponseEntity<?> updateReward(@RequestBody RewardDTO rewardDTO) {
        return rewardService.updateReward(rewardDTO.getId(), rewardDTO.getRewardCategory(), rewardDTO.getName(),
                rewardDTO.getDescription()) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<String> addReward(@RequestBody RewardDTO rewardDTO) {
        Reward reward = new Reward();
        reward.setName(rewardDTO.getName());
        reward.setRewardCategory(rewardDTO.getRewardCategory());
        reward.setDescription(rewardDTO.getDescription());

        return rewardService.addReward(reward) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.CONFLICT).body(
                        String.format("Reward with the given name already exists: '%s'", rewardDTO.getName()));
    }

    @DeleteMapping("/{rewardId}")
    public ResponseEntity<?> deleteRewardById(@PathVariable int rewardId) {
        return rewardService.findRewardById(rewardId)
                .map(reward -> {
                    rewardService.deleteRewardById(rewardId);
                    return ResponseEntity.status(HttpStatus.OK).build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
