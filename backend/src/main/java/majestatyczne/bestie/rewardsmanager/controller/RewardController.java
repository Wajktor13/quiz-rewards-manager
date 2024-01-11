package majestatyczne.bestie.rewardsmanager.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardDTO;
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
        int rewardCategoryId = rewardDTO.rewardCategoryDTO() == null ? -1 : rewardDTO.rewardCategoryDTO().id();

        try {
            rewardService.add(rewardDTO.name(), rewardDTO.description(), rewardCategoryId);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
