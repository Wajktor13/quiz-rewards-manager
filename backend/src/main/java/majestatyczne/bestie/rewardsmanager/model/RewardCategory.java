package majestatyczne.bestie.rewardsmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "reward_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_category_id")
    private int id;

    @Column(unique = true, nullable = false)
    private String name;


    @OneToMany(mappedBy = "rewardCategory")
    private List<Reward> rewards;
}
