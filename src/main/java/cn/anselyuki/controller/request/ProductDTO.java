package cn.anselyuki.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDTO {
    //物资名称
    private String name;
    //规格
    private String model;
    //单位
    private String unit;
    //备注
    private String remark;
    private String imageUrl;
    private Integer status;
}

