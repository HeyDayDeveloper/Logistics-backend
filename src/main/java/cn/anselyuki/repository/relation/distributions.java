package cn.anselyuki.repository.relation;

import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_distributions")
@NoArgsConstructor
public class distributions {
    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 36)
    private String distributionId;
    @Column(length = 36)
    private String demandId;
}
