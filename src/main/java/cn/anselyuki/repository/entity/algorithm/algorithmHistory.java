package cn.anselyuki.repository.entity.algorithm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_algorithm_history")
@NoArgsConstructor
public class algorithmHistory {
    @Id
    private String id;
    private String distributionID;
    private String inputJson;
    private String outputJson;
    private Date createTime;
}
