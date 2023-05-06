package cn.anselyuki.controller;

import cn.anselyuki.common.utils.JpaUtils;
import cn.anselyuki.controller.response.Result;
import cn.anselyuki.repository.InStockRepository;
import cn.anselyuki.repository.entity.InStock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "InStock", description = "物资分类相关接口")
@RequestMapping("inStock")
@RestController
@RequiredArgsConstructor
public class InStockController {
    private final InStockRepository inStockRepository;

    @PutMapping("add")
    @Operation(summary = "添加物资分类", description = "添加物资分类，UUID由内部生成并返回")
    public ResponseEntity<Result<InStock>> addInStock() {
        InStock inStock;
        try {
            inStock = new InStock();
            inStockRepository.save(inStock);
        } catch (Exception exception) {
            exception.printStackTrace();
            return Result.fail(500, "系统内部失败,请检查日志");
        }
        return Result.success(inStock);
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "删除物资分类", description = "删除物资分类")
    public ResponseEntity<Result<InStock>> deleteInStock(@PathVariable String id) {
        if (!inStockRepository.existsById(id)) return Result.fail(404, "物资不存在");
        try {
            inStockRepository.deleteById(id);
        } catch (Exception e) {
            Result.fail(403, "删除物资失败");
        }
        return Result.success(null);
    }

    @PatchMapping("update")
    @Operation(summary = "更新物资分类", description = "更新物资分类")
    public ResponseEntity<Result<InStock>> updateInStock(@RequestBody InStock inStock) {
        if (inStock.getId() == null || inStock.getId().isBlank()) return Result.fail(404, "物资ID不能为空");
        InStock save = inStockRepository.findById(inStock.getId()).orElse(null);
        if (save != null) {
            // 通过工具类将非空属性拷贝到save中
            JpaUtils.copyNotNullProperties(inStock, save);
        } else {
            return Result.fail(404, "该物资不存在");
        }
        try {
            inStockRepository.save(save);
        } catch (Exception e) {
            return Result.fail(403, "更新物资失败");
        }
        return Result.success(save);
    }

    @GetMapping("list")
    @Operation(summary = "获取分类列表", description = "获取分类列表")
    public ResponseEntity<Result<List<InStock>>> listInStock() {
        return Result.success(inStockRepository.findAll());
    }

    @GetMapping("query/{id}")
    @Operation(summary = "查询入库单分类", description = "查询入库单分类")
    public ResponseEntity<Result<InStock>> queryInStock(@PathVariable String id) {
        InStock inStock = inStockRepository.findById(id).orElse(null);
        return Result.success(inStock);
    }
}
