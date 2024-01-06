package majestatyczne.bestie.rewardsmanager.controller;

import jakarta.persistence.EntityNotFoundException;
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
        return rewardService
                .findAll()
                .stream()
                .map(RewardDTO::convertToDTO)
                .toList();
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody RewardDTO rewardDTO) {
        int categoryId = rewardDTO.rewardCategoryDTO() == null ? -1 : rewardDTO.rewardCategoryDTO().id();

        try {
            rewardService.update(rewardDTO.id(), categoryId, rewardDTO.name(),
                    rewardDTO.description());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("/all")
    public ResponseEntity<?> updateAll(@RequestBody List<RewardDTO> rewardDTOS) {
        rewardService.updateAll(rewardDTOS);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody RewardDTO rewardDTO) {
        Reward reward = new Reward();
        reward.setName(rewardDTO.name());
        if (rewardDTO.rewardCategoryDTO() == null) {
            reward.setRewardCategory(null);
        }
        else {
            reward.setRewardCategory(rewardCategoryService.findById(rewardDTO.rewardCategoryDTO().id()));
        }
        reward.setDescription(rewardDTO.description());

        return rewardService.add(reward) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.CONFLICT).body(
                        String.format("Reward with the given name already exists: '%s'", rewardDTO.name()));
    }

    @DeleteMapping("/{rewardId}")
    public ResponseEntity<?> deleteById(@PathVariable int rewardId) {
        try {
            rewardService.deleteById(rewardId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
