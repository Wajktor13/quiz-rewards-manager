package majestatyczne.bestie.rewardsmanager.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardCategoryDTO;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import majestatyczne.bestie.rewardsmanager.repository.RewardCategoryRepository;
import majestatyczne.bestie.rewardsmanager.model.RewardCategory;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RewardCategoryService {

    private final RewardCategoryRepository rewardCategoryRepository;

    @Transactional
    public void add(String name) {
        if (findByName(name).isPresent()) {
            throw new EntityExistsException("reward category with given name already exists");
        }

        RewardCategory rewardCategory = new RewardCategory();
        rewardCategory.setName(name);

        rewardCategoryRepository.save(rewardCategory);
    }

    public List<RewardCategory> findAll() {
        return rewardCategoryRepository.findAll();
    }

    public RewardCategory findById(int rewardCategoryId) {
        return rewardCategoryRepository
                .findById(rewardCategoryId)
                .orElseThrow(() -> new EntityNotFoundException("reward category has not been found"));
    }

    @Transactional
    public void update(int rewardCategoryId, String name) {
        RewardCategory rewardCategory = findById(rewardCategoryId);
        rewardCategory.setName(name);
        rewardCategoryRepository.save(rewardCategory);
    }

    @Transactional
    public void deleteById(int rewardCategoryId) {
        findById(rewardCategoryId); // throws exception when not found
        rewardCategoryRepository.deleteById(rewardCategoryId);
    }

    @Transactional
    public void addRewardToCategory(RewardCategory rewardCategory, Reward reward) {
        rewardCategory.getRewards().add(reward);

        rewardCategoryRepository.save(rewardCategory);
    }

    public Optional<RewardCategory> findByName(String name) {
        return Optional.ofNullable(rewardCategoryRepository.findByName(name));
    }

    @Transactional
    public void updateAll(List<RewardCategoryDTO> rewardCategoryDTOS) {
        List<RewardCategory> rewardCategories = rewardCategoryRepository.findAll();

        List<RewardCategory> updatedRewardCategories = rewardCategoryDTOS.stream()
                .map(rewardCategoryDTO -> {
                    RewardCategory matchingRewardCategory = rewardCategories
                            .stream()
                            .filter(reward -> reward.getId() == rewardCategoryDTO.id())
                            .findFirst()
                            .orElse(null);

                    if (matchingRewardCategory != null) {
                        matchingRewardCategory.setName(rewardCategoryDTO.name());
                    }

                    return matchingRewardCategory;

                })
                .toList();

        rewardCategoryRepository.saveAll(updatedRewardCategories);
    }
}
