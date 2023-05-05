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

    @PutMapping("add")
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

    @PatchMapping("update")
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

    /**
     * 物资分类删除
     * 通过PathVariable接收id进行删除
     *
     * @param id 物资ID
     * @return 删除执行状态
     */
    @DeleteMapping("delete/{id}")
    @Operation(summary = "删除物资分类", description = "根据ID删除物资")
    public ResponseEntity<Result<Object>> deleteProductCategory(@PathVariable String id) {
        if (!productCategoryRepository.existsById(id))
            return Result.fail(404, "物资分类不存在");
        try {
            productCategoryRepository.deleteById(id);
        } catch (Exception e) {
            Result.fail(403, "删除物资分类失败");
        }
        return Result.success(null);
    }

    /**
     * 物资分类查询
     * 通过PathVariable接收name进行查询
     *
     * @param name 物资分类名称
     * @return 查询结果
     */
    @GetMapping("query/{name}")
    @Operation(summary = "查询物资分类", description = "根据ID查询物资")
    public ResponseEntity<Result<ProductCategory>> queryProductCategory(@PathVariable String name) {
        if (!productCategoryRepository.existsByName(name))
            return Result.fail(404, "物资分类不存在");
        ProductCategory category = productCategoryRepository.findByName(name);
        return Result.success(category);
    }
}
