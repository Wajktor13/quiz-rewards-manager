package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardCategoryDTO;
import majestatyczne.bestie.rewardsmanager.model.RewardCategory;
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

    @GetMapping
    public List<RewardCategory> getAllRewardCategories() {
        return rewardCategoryService.findAllRewardCategories();
    }

    @PostMapping
    public void addRewardCategory(@RequestBody RewardCategoryDTO rewardCategoryDTO) {
        rewardCategoryService.addRewardCategory(rewardCategoryDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateRewardCategory(@RequestBody RewardCategoryDTO rewardCategoryDTO) {
        return rewardCategoryService.updateRewardCategory(rewardCategoryDTO) ?
                ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRewardCategory(@RequestBody RewardCategoryDTO rewardCategoryDTO) {
        rewardCategoryService.deleteRewardCategory(rewardCategoryDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
