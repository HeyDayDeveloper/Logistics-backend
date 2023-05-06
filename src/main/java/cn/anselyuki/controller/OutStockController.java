package cn.anselyuki.controller;

import cn.anselyuki.common.utils.JpaUtils;
import cn.anselyuki.controller.response.Result;
import cn.anselyuki.repository.OutStockRepository;
import cn.anselyuki.repository.entity.OutStock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "OutStock", description = "物资分类相关接口")
@RequestMapping("outStock")
@RestController
@RequiredArgsConstructor
public class OutStockController {
    private final OutStockRepository outStockRepository;

    @PutMapping("add")
    @Operation(summary = "添加物资分类", description = "添加物资分类，UUID由内部生成并返回")
    public ResponseEntity<Result<OutStock>> addInStock() {
        OutStock outStock;
        try {
            outStock = new OutStock();
            outStockRepository.save(outStock);
        } catch (Exception exception) {
            exception.printStackTrace();
            return Result.fail(500, "系统内部失败,请检查日志");
        }
        return Result.success(outStock);
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "删除物资分类", description = "删除物资分类")
    public ResponseEntity<Result<Void>> deleteInStock(@PathVariable String id) {
        if (!outStockRepository.existsById(id)) return Result.fail(404, "物资不存在");
        try {
            outStockRepository.deleteById(id);
        } catch (Exception e) {
            Result.fail(403, "删除物资失败");
        }
        return Result.success(null);
    }

    @PatchMapping("update")
    @Operation(summary = "更新物资分类", description = "更新物资分类")
    public ResponseEntity<Result<OutStock>> updateInStock(@RequestBody OutStock outStock) {
        if (outStock.getId() == null || outStock.getId().isBlank()) return Result.fail(404, "物资ID不能为空");
        OutStock save = outStockRepository.findById(outStock.getId()).orElse(null);
        if (save != null) {
            // 通过工具类将非空属性拷贝到save中
            JpaUtils.copyNotNullProperties(outStock, save);
        } else {
            return Result.fail(404, "该物资不存在");
        }
        try {
            outStockRepository.save(save);
        } catch (Exception e) {
            return Result.fail(403, "更新物资失败");
        }
        return Result.success(save);
    }

    @GetMapping("list")
    @Operation(summary = "获取分类列表", description = "获取分类列表")
    public ResponseEntity<Result<List<OutStock>>> listInStock() {
        return Result.success(outStockRepository.findAll());
    }

    @GetMapping("query/{id}")
    @Operation(summary = "查询入库单分类", description = "查询入库单分类")
    public ResponseEntity<Result<OutStock>> queryInStock(@PathVariable String id) {
        OutStock outStock = outStockRepository.findById(id).orElse(null);
        return Result.success(outStock);
    }
}
