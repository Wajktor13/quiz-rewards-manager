package majestatyczne.bestie.rewardsmanager.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardCategoryDTO;
import majestatyczne.bestie.rewardsmanager.service.RewardCategoryService;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(RewardCategoryDTO.convertToDTO(rewardCategoryService.findById(rewardCategoryId)));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public List<RewardCategoryDTO> getAll() {
        return rewardCategoryService
                .findAll()
                .stream()
                .map(RewardCategoryDTO::convertToDTO)
                .toList();
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody RewardCategoryDTO rewardCategoryDTO) {
        try {
            rewardCategoryService.add(rewardCategoryDTO.name());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody RewardCategoryDTO rewardCategoryDTO) {
        try {
            rewardCategoryService.update(rewardCategoryDTO.id(), rewardCategoryDTO.name());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/all")
    public ResponseEntity<?> updateAll(@RequestBody List<RewardCategoryDTO> rewardCategoryDTOS) {
        rewardCategoryService.updateAll(rewardCategoryDTOS);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{rewardCategoryId}")
    public ResponseEntity<?> deleteById(@PathVariable int rewardCategoryId) {
        try {
            rewardCategoryService.deleteById(rewardCategoryId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
        }
    }
}
