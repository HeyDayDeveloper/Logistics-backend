package cn.anselyuki.repository.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_product_category")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String remark;
    private Integer sort;
    private Date createTime;
    private Date modifiedTime;
    private String pid;
}
