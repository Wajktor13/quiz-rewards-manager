package majestatyczne.bestie.rewardsmanager.controller;

import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardCategoryDTO;
import majestatyczne.bestie.rewardsmanager.model.RewardCategory;
import majestatyczne.bestie.rewardsmanager.service.RewardCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        RewardCategory rewardCategory = new RewardCategory(
                rewardCategoryDTO.getId(),
                rewardCategoryDTO.getName(),
                rewardCategoryDTO.getPriority(),
                new ArrayList<>()
        );

        rewardCategoryService.addRewardCategory(rewardCategory);
    }
}
