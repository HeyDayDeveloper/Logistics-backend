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
@Table(schema = "logisticSystem", name = "tb_product")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
public class Product {
    //物资ID
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    //物资名称
    private String name;
    //规格
    private String model;
    //单位
    private String unit;
    //备注
    private String remark;
    private Integer sort;
    private Date createTime;
    private Date modifiedTime;
    private String imageUrl;
    private Integer status;
}
