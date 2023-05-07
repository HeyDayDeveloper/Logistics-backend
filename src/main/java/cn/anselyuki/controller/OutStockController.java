package cn.anselyuki.controller;

import cn.anselyuki.common.utils.JpaUtils;
import cn.anselyuki.controller.response.OutStockVO;
import cn.anselyuki.controller.response.Result;
import cn.anselyuki.repository.OutStockRepository;
import cn.anselyuki.repository.entity.OutStock;
import cn.anselyuki.service.OutStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@Tag(name = "OutStock", description = "出库单相关接口")
@RequestMapping("outStock")
@RestController
@RequiredArgsConstructor
public class OutStockController {
    private final OutStockRepository outStockRepository;
    private final OutStockService outStockService;

    @PutMapping("add")
    @Operation(summary = "添加出库单", description = "添加出库单，UUID由内部生成并返回")
    public ResponseEntity<Result<OutStock>> addInStock(@RequestBody OutStock outStock) {
        try {
            outStock.setCreateTime(new Date());
            outStockRepository.save(outStock);
        } catch (Exception exception) {
            exception.printStackTrace();
            return Result.fail(500, "系统内部失败,请检查日志");
        }
        return Result.success(outStock);
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "删除出库单", description = "删除出库单")
    public ResponseEntity<Result<Void>> deleteInStock(@PathVariable String id) {
        if (!outStockRepository.existsById(id)) return Result.fail(404, "出库单不存在");
        try {
            outStockRepository.deleteById(id);
        } catch (Exception e) {
            return Result.fail(403, "删除出库单失败");
        }
        return Result.success(null);
    }

    @PatchMapping("update")
    @Operation(summary = "更新出库单", description = "更新出库单")
    public ResponseEntity<Result<OutStock>> updateInStock(@RequestBody OutStock outStock) {
        if (outStock.getId() == null || outStock.getId().isBlank()) return Result.fail(404, "出库单ID不能为空");
        OutStock save = outStockRepository.findById(outStock.getId()).orElse(null);
        if (save != null) {
            // 通过工具类将非空属性拷贝到save中
            JpaUtils.copyNotNullProperties(outStock, save);
        } else {
            return Result.fail(404, "该出库单不存在");
        }
        try {
            outStockRepository.save(save);
        } catch (Exception e) {
            return Result.fail(403, "更新出库单失败");
        }
        return Result.success(save);
    }

    @GetMapping("list")
    @Operation(summary = "获取出库单列表", description = "获取出库单列表")
    public ResponseEntity<Result<List<OutStockVO>>> listInStock() {
        return Result.success(outStockService.listForOutStock());
    }

    @GetMapping("query/{id}")
    @Operation(summary = "查询出库单", description = "查询出库单")
    public ResponseEntity<Result<OutStockVO>> queryInStock(@PathVariable String id) {
        return Result.success(outStockService.queryForOutStock(id));
    }
}
