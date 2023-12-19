package majestatyczne.bestie.rewardsmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizDTO {

    @JsonProperty("quiz_id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("maxScore")
    private int maxScore;

    @JsonProperty("date")
    private Date date;
}

