package cn.anselyuki.controller;

import cn.anselyuki.common.utils.JpaUtils;
import cn.anselyuki.controller.request.ProductCategoryDTO;
import cn.anselyuki.controller.response.Result;
import cn.anselyuki.repository.ProductCategoryRepository;
import cn.anselyuki.repository.entity.ProductCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author AnselYuki
 */
@Slf4j
@Tag(name = "Category", description = "物资分类相关接口")
@RequestMapping("category")
@RestController
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryRepository productCategoryRepository;

    @PostMapping("add")
    @Operation(summary = "新增物资分类", description = "新增物资分类")
    public ResponseEntity<Result<ProductCategory>> addCategory(@Valid @RequestBody ProductCategoryDTO productCategoryDTO) {
        ProductCategory category = new ProductCategory(productCategoryDTO);
        category.setCreateTime(new Date());
        try {
            productCategoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            return Result.fail(403, "物资分类已经存在");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(500, "系统内部失败,请检查日志");
        }
        return Result.success(category);
    }

    @PutMapping("update")
    @Operation(summary = "更新物资分类", description = "通过传入的ID更新物资分类")
    public ResponseEntity<Result<ProductCategory>> updateCategory(@RequestBody ProductCategory category) {
        if (category.getId().isBlank()) {
            return Result.fail(403, "物资分类ID不能为空");
        }
        ProductCategory save = productCategoryRepository.findById(category.getId()).orElse(null);
        if (save != null) {
            // 通过工具类将非空属性copy到save中
            JpaUtils.copyNotNullProperties(category, save);
            // 设置修改时间
            save.setModifiedTime(new Date());
        } else {
            return Result.fail(404, "物资分类不存在");
        }
        try {
            productCategoryRepository.save(save);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(500, "系统内部失败,请检查日志");
        }
        return Result.success(save);
    }
}
