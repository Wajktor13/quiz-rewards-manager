package majestatyczne.bestie.rewardsmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import majestatyczne.bestie.rewardsmanager.reward_selection_strategy.RewardStrategyType;

import java.util.List;

@Entity
@Table(name = "reward_strategy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "quiz")
public class RewardStrategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_strategy_id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RewardStrategyType rewardStrategyType;

//    @OneToOne(mappedBy = "rewardStrategy")
    @OneToOne
    @JoinColumn(name="quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "rewardStrategy")
    private List<RewardStrategyParameter> parameters;
}
