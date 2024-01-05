package majestatyczne.bestie.rewardsmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "reward_strategy_parameter")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "rewardStrategy")
public class RewardStrategyParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_strategy_parameter_id")
    private int id;

    @Column(nullable = false)
    private int priority;

    @Column(nullable = false)
    private int parameterValue;

    @ManyToOne
    @JoinColumn(name = "reward_category_id")
    private RewardCategory rewardCategory;

    @ManyToOne
    @JoinColumn(name = "reward_strategy_id")
    private RewardStrategy rewardStrategy;
}
