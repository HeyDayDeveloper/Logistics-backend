package cn.anselyuki.repository.entity.algorithm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_algorithm_log")
@NoArgsConstructor
public class algorithmLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String applyName;
    private String distributionId;
    private String remark;
}
