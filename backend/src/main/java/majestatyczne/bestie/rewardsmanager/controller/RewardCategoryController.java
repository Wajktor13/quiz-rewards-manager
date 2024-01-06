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

    @GetMapping("/{rewardCategoryId}")
    public ResponseEntity<?> getById(@PathVariable int rewardCategoryId) {
        return rewardCategoryService
                .findById(rewardCategoryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<RewardCategoryDTO> getAll() {
        return rewardCategoryService.findAll().stream()
                .map(category -> new RewardCategoryDTO(category.getId(), category.getName()))
                .toList();
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody RewardCategoryDTO rewardCategoryDTO) {
        RewardCategory rewardCategory = new RewardCategory();
        rewardCategory.setName(rewardCategoryDTO.name());

        return rewardCategoryService.add(rewardCategory) ?
                ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.CONFLICT).body(
                String.format("Category with the given name already exists: '%s'", rewardCategoryDTO.name())
        );
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody RewardCategoryDTO rewardCategoryDTO) {
        return rewardCategoryService.update(rewardCategoryDTO.id(), rewardCategoryDTO.name()) ?
                ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/all")
    public ResponseEntity<?> updateAll(@RequestBody List<RewardCategoryDTO> rewardCategoryDTOS) {
        rewardCategoryService.updateAll(rewardCategoryDTOS);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{rewardCategoryId}")
    public ResponseEntity<?> deleteById(@PathVariable int rewardCategoryId) {
        return rewardCategoryService.findById(rewardCategoryId)
                .map(category -> {
                    rewardCategoryService.deleteById(rewardCategoryId);
                    return ResponseEntity.status(HttpStatus.OK).build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
