package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardCategoryDTO;
import majestatyczne.bestie.rewardsmanager.service.RewardCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reward-categories")
@RequiredArgsConstructor
public class RewardCategoryController {

    private final RewardCategoryService rewardCategoryService;

    @GetMapping("/{rewardCategoryId}")
    public ResponseEntity<?> getRewardCategoryById(@PathVariable int rewardCategoryId) {
        return rewardCategoryService
                .findRewardCategoryById(rewardCategoryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<RewardCategoryDTO> getAllRewardCategories() {
        return rewardCategoryService.findAllRewardCategories().stream()
                .map(category -> new RewardCategoryDTO(category.getId(), category.getName()))
                .toList();
    }

    @PostMapping
    public ResponseEntity<?> addRewardCategory(@RequestBody RewardCategoryDTO rewardCategoryDTO) {
        return rewardCategoryService.addRewardCategory(rewardCategoryDTO) ?
                ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<?> updateRewardCategory(@RequestBody RewardCategoryDTO rewardCategoryDTO) {
        return rewardCategoryService.updateRewardCategory(rewardCategoryDTO) ?
                ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/all")
    public ResponseEntity<?> updateRewardCategories(@RequestBody List<RewardCategoryDTO> rewardCategoryDTOS) {
        rewardCategoryService.updateRewardCategories(rewardCategoryDTOS);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{rewardCategoryId}")
    public ResponseEntity<?> deleteRewardCategoryById(@PathVariable int rewardCategoryId) {
        return rewardCategoryService.findRewardCategoryById(rewardCategoryId)
                .map(category -> {
                    rewardCategoryService.deleteRewardCategoryById(rewardCategoryId);
                    return ResponseEntity.status(HttpStatus.OK).build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
