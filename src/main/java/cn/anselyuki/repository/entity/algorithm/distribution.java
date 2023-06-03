package cn.anselyuki.repository.entity.algorithm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_distribution")
@NoArgsConstructor
public class distribution {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String address;
    private String remark;
}
