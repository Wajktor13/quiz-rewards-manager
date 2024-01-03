package majestatyczne.bestie.rewardsmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import majestatyczne.bestie.rewardsmanager.reward_selection_strategy.RewardSelectionStrategyType;

import java.util.List;

@Entity
@Table(name = "reward_selection")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_selection_id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RewardSelectionStrategyType rewardSelectionStrategyType;

    @OneToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "rewardSelection")
    private List<RewardSelectionParameters> parameters;
}
