package majestatyczne.bestie.rewardsmanager.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardDTO;
import majestatyczne.bestie.rewardsmanager.model.RewardCategory;
import majestatyczne.bestie.rewardsmanager.repository.RewardRepository;
import majestatyczne.bestie.rewardsmanager.model.Reward;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RewardService {

    private final RewardRepository rewardRepository;

    private final RewardCategoryService rewardCategoryService;

    @Transactional
    public void add(String name, String description, int rewardCategoryId) {
        if (findByName(name).isPresent()) {
            throw new EntityExistsException(String.format("Reward with the the given name already exists: '%s'",
                    name));
        }

        RewardCategory rewardCategory = rewardCategoryService.findById(rewardCategoryId);

        Reward reward = new Reward();
        reward.setName(name);
        reward.setDescription(description);
        reward.setRewardCategory(rewardCategory);

        rewardRepository.save(reward);

        rewardCategoryService.addRewardToCategory(reward, rewardCategory);
    }
    @Transactional
    public void add(String name, String description) {
        if (findByName(name).isPresent()) {
            throw new EntityExistsException(String.format("Reward with the the given name already exists: '%s'",
                    name));
        }
        Reward reward = new Reward();
        reward.setName(name);
        reward.setDescription(description);
        rewardRepository.save(reward);
    }

    public Optional<Reward> findByName(String name) {
        return Optional.ofNullable(rewardRepository.findByName(name));
    }

    public List<Reward> findAllByNames(List<String> names) {
        return rewardRepository.findAllByNames(names);
    }

    @Transactional
    public void addAllWithoutDuplicates(List<Reward> rewards) {
        List<String> names = rewards
                .stream()
                .map(Reward::getName)
                .toList();

        List<Reward> alreadyAddedRewards = findAllByNames(names);

        List<Reward> newRewards = rewards
                .stream()
                .filter(reward ->
                        alreadyAddedRewards
                        .stream()
                        .noneMatch(alreadyAddedReward -> alreadyAddedReward.getName().equals(reward.getName())))
                .toList();

        rewardRepository.saveAll(newRewards);
    }

    public List<Reward> findAll() {
        return rewardRepository.findAll();
    }

    public Reward findById(int rewardId) {
        return rewardRepository
                .findById(rewardId)
                .orElseThrow(() -> new EntityNotFoundException("reward has not been found"));
    }

    @Transactional
    public void update(Reward reward, RewardCategory rewardCategory, String name, String description) {
        if (reward.getRewardCategory() != null) {
            rewardCategoryService.removeRewardFromCategory(reward, rewardCategory);
        }

        reward.setRewardCategory(rewardCategory);
        reward.setName(name);
        reward.setDescription(description);

        rewardRepository.save(reward);

        if (rewardCategory != null) {
            rewardCategoryService.addRewardToCategory(reward, rewardCategory);
        }
    }

    @Transactional
    public void update(int rewardId, int rewardCategoryId, String name, String description) {
        Reward reward = findById(rewardId);
        RewardCategory rewardCategory = rewardCategoryId == -1 ? null :
                rewardCategoryService.findById(rewardCategoryId);

        update(reward, rewardCategory, name, description);
    }

    @Transactional
    public void updateAll(List<RewardDTO> rewardDTOS) {
        List<Reward> rewards = rewardRepository.findAll();

        List<Reward> updatedRewards = rewardDTOS.stream()
                .map(rewardDTO -> updateRewardFromDTO(rewards, rewardDTO))
                .filter(Objects::nonNull)
                .toList();

        rewardRepository.saveAll(updatedRewards);
    }

    private Reward updateRewardFromDTO(List<Reward> rewards, RewardDTO rewardDTO) {
        Reward matchingReward = rewards.stream()
                .filter(reward -> reward.getId() == rewardDTO.id())
                .findFirst()
                .orElse(null);

        if (matchingReward == null) {
            return null;
        }

        matchingReward.setName(rewardDTO.name());

        if (rewardDTO.rewardCategoryDTO() == null) {
            matchingReward.setRewardCategory(null);
        } else {
            RewardCategory rewardCategory = rewardCategoryService.findById(rewardDTO.rewardCategoryDTO().id());

            rewardCategoryService.removeRewardFromCategory(matchingReward, matchingReward.getRewardCategory());

            matchingReward.setRewardCategory(rewardCategory);

            rewardCategoryService.addRewardToCategory(matchingReward, rewardCategory);
        }
        matchingReward.setDescription(rewardDTO.description());

        return matchingReward;
    }

    @Transactional
    public void deleteById(int rewardId) {
        findById(rewardId); // throws exception when not found
        rewardRepository.deleteById(rewardId);
    }
}
