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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    //由于产品可能拥有同名现象，此处不使用唯一性约束
    private String name;
    private Integer num;
    private String categoryId;
    private Date createTime;
    private Date modifiedTime;
    private String remark;
    private String unit;
    //需求紧迫度
    private String degree;

    public Product(ProductDTO productDTO) {
        BeanUtils.copyProperties(productDTO, this);
    }
}
