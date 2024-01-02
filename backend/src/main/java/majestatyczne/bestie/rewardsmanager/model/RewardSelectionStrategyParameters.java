package majestatyczne.bestie.rewardsmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reward_selection_strategy_parameters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardSelectionStrategyParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_selection_strategy_parameters_id")
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float value;

    @ManyToOne
    @JoinColumn(name = "reward_selection_strategy_id")
    private RewardSelectionStrategy rewardSelectionStrategy;
}
