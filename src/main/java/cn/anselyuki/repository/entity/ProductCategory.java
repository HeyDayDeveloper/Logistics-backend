package cn.anselyuki.repository.entity;

import cn.anselyuki.controller.request.ProductCategoryDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String name;
    private String remark;
    private Date createTime;
    private Date modifiedTime;

    public ProductCategory(ProductCategoryDTO productCategoryDTO) {
        BeanUtils.copyProperties(productCategoryDTO, this);
    }
}
