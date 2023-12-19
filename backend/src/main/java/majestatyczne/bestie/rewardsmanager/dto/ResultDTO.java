package majestatyczne.bestie.rewardsmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.Person;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultDTO {

    @JsonProperty("result_id")
    private int id;

    @JsonProperty("person_id")
    private Person person;

    @JsonProperty("startDate")
    private Date startDate;

    @JsonProperty("endDate")
    private Date endDate;

    @JsonProperty("score")
    private int score;
}

