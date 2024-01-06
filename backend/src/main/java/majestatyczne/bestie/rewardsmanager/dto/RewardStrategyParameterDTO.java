package majestatyczne.bestie.rewardsmanager.dto;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.RewardCategory;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategy;
import majestatyczne.bestie.rewardsmanager.model.RewardStrategyParameter;
import majestatyczne.bestie.rewardsmanager.service.RewardCategoryService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RewardStrategyParameterDTO {

    private int id;

    private int priority;

    private int parameterValue;

    private RewardCategoryDTO rewardCategory;

    public static RewardStrategyParameterDTO convertToDTO(RewardStrategyParameter rewardStrategyParameter) {
        return new RewardStrategyParameterDTO(rewardStrategyParameter.getId(), rewardStrategyParameter.getPriority(),
                rewardStrategyParameter.getParameterValue(), RewardCategoryDTO.fromRewardCategory(rewardStrategyParameter.getRewardCategory()));
    }

    public static List<RewardStrategyParameterDTO> convertAllToDTO(List<RewardStrategyParameter> rewardStrategyParameters) {
        return rewardStrategyParameters.stream().map(RewardStrategyParameterDTO::convertToDTO).toList();
    }

    public static RewardStrategyParameter convertFromDTO(RewardStrategyParameterDTO rewardStrategyParameterDTO,
                                                  RewardStrategy rewardStrategy, RewardCategory rewardCategory) {
        RewardStrategyParameter rewardStrategyParameter = new RewardStrategyParameter();
        rewardStrategyParameter.setRewardStrategy(rewardStrategy);
        rewardStrategyParameter.setParameterValue(rewardStrategyParameterDTO.getParameterValue());
        rewardStrategyParameter.setPriority(rewardStrategyParameterDTO.getPriority());

        if (rewardStrategyParameterDTO.getRewardCategory() == null) {
            throw new IllegalStateException("reward category cannot be null");
        }
        rewardStrategyParameter.setRewardCategory(rewardCategory);

        return rewardStrategyParameter;
    }

    public static List<RewardStrategyParameter> convertAllFromDTO(List<RewardStrategyParameterDTO> rewardStrategyParameterDTOs,
                                                                  RewardStrategy rewardStrategy,
                                                                  RewardCategoryService rewardCategoryService) {
        return rewardStrategyParameterDTOs
                .stream()
                .map(rewardStrategyParameterDTO ->
                {
                    if (rewardStrategyParameterDTO == null) {
                        throw new IllegalStateException("reward category in parameter cannot be null");
                    }

                    Optional<RewardCategory> rewardCategory = rewardCategoryService.findRewardCategoryById(
                            rewardStrategyParameterDTO.getRewardCategory().getId());

                    if (rewardCategory.isEmpty()) {
                        throw new EntityNotFoundException("reward category has not been found");
                    }

                    return RewardStrategyParameterDTO.convertFromDTO(rewardStrategyParameterDTO, rewardStrategy,
                            rewardCategory.get());
                })
                .toList();
    }
}
