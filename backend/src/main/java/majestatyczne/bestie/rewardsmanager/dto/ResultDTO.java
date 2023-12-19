package majestatyczne.bestie.rewardsmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import majestatyczne.bestie.rewardsmanager.model.Person;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

