package cn.anselyuki.repository.entity;

import cn.anselyuki.controller.request.ProductDTO;
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
@Table(schema = "logisticSystem", name = "tb_product")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Integer num;
    private String category;
    private Date createTime;
    private Date modifiedTime;
    private String remark;
    private String unit;
    private String degree;

    public Product(ProductDTO productDTO) {
        BeanUtils.copyProperties(productDTO, this);
    }
}
