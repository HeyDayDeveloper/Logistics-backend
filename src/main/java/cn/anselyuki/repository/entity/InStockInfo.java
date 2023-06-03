package cn.anselyuki.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_in_stock_info")
@NoArgsConstructor
public class InStockInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String pid;
    private Integer count;
    private Date createTime;
    private Date modifiedTime;
}
