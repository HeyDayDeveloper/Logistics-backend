package cn.anselyuki.repository.entity;

import cn.anselyuki.controller.request.ProductDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_product")
@NoArgsConstructor
public class Product {
    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    //由于产品可能拥有同名现象，此处不使用唯一性约束
    @Column(length = 32)
    private String name;
    private Integer num;
    @Column(length = 36)
    private String categoryId;
    private Date createTime;
    private Date modifiedTime;
    @Column(length = 127)
    private String remark;
    @Column(length = 16)
    private String unit;
    //需求紧迫度
    private String degree;

    public Product(ProductDTO productDTO) {
        BeanUtils.copyProperties(productDTO, this);
    }
}
