package cn.anselyuki.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author AnselYuki
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductCategoryDTO {
    @NotBlank(message = "物资分类名称不能为空")
    private String name;
    @NotBlank(message = "物资分类备注不能为空")
    private String remark;
}
