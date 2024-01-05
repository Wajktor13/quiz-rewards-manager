package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardDTO;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import majestatyczne.bestie.rewardsmanager.service.RewardCategoryService;
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
    private final RewardCategoryService rewardCategoryService;

    @GetMapping
    public List<RewardDTO> getAllRewards() {
        return rewardService.findAllRewards();
    }

    @PutMapping
    public ResponseEntity<?> updateReward(@RequestBody RewardDTO rewardDTO) {
        var category = rewardDTO.getRewardCategory() == null ? null :
                rewardCategoryService.findRewardCategoryById(rewardDTO.getRewardCategory().getId()).orElse(null);

        return rewardService.updateReward(rewardDTO.getId(), category, rewardDTO.getName(),
                rewardDTO.getDescription()) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PutMapping("/all")
    public ResponseEntity<?> updateRewards(@RequestBody List<RewardDTO> rewardDTOS) {
        rewardService.updateRewards(rewardDTOS);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<String> addReward(@RequestBody RewardDTO rewardDTO) {
        Reward reward = new Reward();
        reward.setName(rewardDTO.getName());
        if (rewardDTO.getRewardCategory() == null) {
            reward.setRewardCategory(null);
        }
        else {
            reward.setRewardCategory(rewardCategoryService.findRewardCategoryById(rewardDTO.getRewardCategory().getId()).orElse(null));
        }
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
