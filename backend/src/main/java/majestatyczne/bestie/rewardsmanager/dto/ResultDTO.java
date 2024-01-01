package majestatyczne.bestie.rewardsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import majestatyczne.bestie.rewardsmanager.model.Person;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultDTO {

    private int id;

    private Person person;

    private Date startDate;

    private Date endDate;

    private int score;
}

