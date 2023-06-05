package cn.anselyuki.repository.relation;

import cn.anselyuki.controller.request.ProductCategoryDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(schema = "logisticSystem", name = "tb_product_category")
@NoArgsConstructor
public class ProductCategory {
    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, length = 32)
    private String name;
    @Column(length = 127)
    private String remark;

    private Date createTime;
    private Date modifiedTime;

    public ProductCategory(ProductCategoryDTO productCategoryDTO) {
        BeanUtils.copyProperties(productCategoryDTO, this);
    }
}
