package cn.anselyuki.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author AnselYuki
 */
@Data
public class ProductCategoryDTO {
    @NotBlank(message = "物资分类名称不能为空")
    private String name;
    private String remark;
}
