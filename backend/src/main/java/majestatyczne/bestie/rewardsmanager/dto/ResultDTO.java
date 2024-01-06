package majestatyczne.bestie.rewardsmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import majestatyczne.bestie.rewardsmanager.model.Person;
import majestatyczne.bestie.rewardsmanager.model.Result;

import java.util.Date;

public record ResultDTO (
        int id,

        Person person,

        Date startDate,

        Date endDate,

        int score,

        @JsonProperty("reward")
        RewardDTO rewardDTO
) {

    public static ResultDTO convertToDTO(Result result) {
        return new ResultDTO(result.getId(), result.getPerson(), result.getStartDate(), result.getEndDate(),
                result.getScore(), RewardDTO.convertToDTO(result.getReward()));
    }
}
