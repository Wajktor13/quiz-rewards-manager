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
    public List<RewardDTO> getAll() {
        return rewardService.findAll();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody RewardDTO rewardDTO) {
        var category = rewardDTO.getRewardCategory() == null ? null :
                rewardCategoryService.findById(rewardDTO.getRewardCategory().getId()).orElse(null);

        return rewardService.update(rewardDTO.getId(), category, rewardDTO.getName(),
                rewardDTO.getDescription()) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PutMapping("/all")
    public ResponseEntity<?> updateAll(@RequestBody List<RewardDTO> rewardDTOS) {
        rewardService.updateAll(rewardDTOS);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody RewardDTO rewardDTO) {
        Reward reward = new Reward();
        reward.setName(rewardDTO.getName());
        if (rewardDTO.getRewardCategory() == null) {
            reward.setRewardCategory(null);
        }
        else {
            reward.setRewardCategory(rewardCategoryService.findById(rewardDTO.getRewardCategory().getId()).orElse(null));
        }
        reward.setDescription(rewardDTO.getDescription());

        return rewardService.add(reward) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.CONFLICT).body(
                        String.format("Reward with the given name already exists: '%s'", rewardDTO.getName()));
    }

    @DeleteMapping("/{rewardId}")
    public ResponseEntity<?> deleteById(@PathVariable int rewardId) {
        return rewardService.findById(rewardId)
                .map(reward -> {
                    rewardService.deleteById(rewardId);
                    return ResponseEntity.status(HttpStatus.OK).build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
