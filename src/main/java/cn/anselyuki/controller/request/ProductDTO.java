package cn.anselyuki.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDTO {
    @NotBlank(message = "产品名称不能为空")
    private String name;
    @NotEmpty(message = "产品数量不能为空")
    private Integer num;
    private String categoryId;
    private String remark;
    private String unit;
}

