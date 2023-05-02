package cn.anselyuki.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDTO {
    @NotBlank(message = "产品名称不能为空")
    private String name;
    @NotNull(message = "产品数量不能为空")
    private Integer num;
    private String categoryId;
    private String category;
    private String remark;
    private String unit;
}

