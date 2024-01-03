package majestatyczne.bestie.rewardsmanager.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import majestatyczne.bestie.rewardsmanager.dto.RewardStrategyParameterDTO;
import majestatyczne.bestie.rewardsmanager.model.RewardCategory;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategy;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategyParameter;
import majestatyczne.bestie.rewardsmanager.repository.RewardStrategyParameterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RewardStrategyParameterService {

    private final RewardStrategyParameterRepository rewardStrategyParameterRepository;

    private final RewardCategoryService rewardCategoryService;

    @Transactional
    public List<RewardStrategyParameter> addAllRewardStrategyParameters(List<RewardStrategyParameterDTO> rewardStrategyParameterDTOs,
                                                                  RewardStrategy rewardStrategy) {

        List<RewardStrategyParameter> rewardStrategyParameters = rewardStrategyParameterDTOs
                .stream()
                .map(rewardStrategyParameterDTO -> {
                        RewardStrategyParameter rewardStrategyParameter = new RewardStrategyParameter();
                        rewardStrategyParameter.setPriority(rewardStrategyParameterDTO.getPriority());
                        rewardStrategyParameter.setParameterValue(rewardStrategyParameterDTO.getParameterValue());
                        rewardStrategyParameter.setRewardStrategy(rewardStrategy);

                        Optional<RewardCategory> rewardCategory =  rewardCategoryService
                                .findRewardCategoryById(rewardStrategyParameterDTO.getId());
                        if (rewardCategory.isPresent()) {
                            rewardStrategyParameter.setRewardCategory(rewardCategory.get());
                        } else {
                            throw new EntityNotFoundException();
                        }

                        return rewardStrategyParameter;
                    })
                .toList();

        rewardStrategyParameterRepository.saveAll(rewardStrategyParameters);

        return rewardStrategyParameters;
    }
}
