package cn.anselyuki.repository.relation;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String distributionId;
    private String demandId;
}
