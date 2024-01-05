package majestatyczne.bestie.rewardsmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "quiz")
@Data
@NoArgsConstructor
@ToString(exclude = "results")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int maxScore;

    @Column(nullable = false)
    private Date date;

//    @JsonIgnore
    @OneToMany(mappedBy = "quiz")
    private List<Result> results;

    @OneToOne(mappedBy = "quiz")
    private RewardStrategy rewardStrategy;
}
