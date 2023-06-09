package cn.anselyuki.controller.response;

import cn.anselyuki.repository.entity.Product;
import cn.anselyuki.repository.relation.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
public class ProductVO {
    private String id;
    private String name;
    private Integer num;
    private ProductCategory category;
    @JsonIgnore
    private String categoryId;
    private Date createTime;
    private Date modifiedTime;
    private String remark;
    private String unit;
    private String degree;

    public static ProductVO convert(Product product) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(product, productVO);
        return productVO;
    }
}
