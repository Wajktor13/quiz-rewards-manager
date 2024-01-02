package majestatyczne.bestie.rewardsmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import majestatyczne.bestie.rewardsmanager.enums.RewardSelectionStrategyType;

import java.util.List;

@Entity
@Table(name = "reward_selection_strategy")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardSelectionStrategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_selection_strategy_id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RewardSelectionStrategyType rewardSelectionStrategyType;

    @OneToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "rewardSelectionStrategy")
    private List<RewardSelectionStrategyParameters> parameters;
}
