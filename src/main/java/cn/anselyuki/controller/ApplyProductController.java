package cn.anselyuki.controller;

import cn.anselyuki.common.utils.JpaUtils;
import cn.anselyuki.controller.request.ApplyInfoDTO;
import cn.anselyuki.controller.response.ApplyInfoVO;
import cn.anselyuki.controller.response.Result;
import cn.anselyuki.repository.ApplyProductRepository;
import cn.anselyuki.repository.entity.ApplyProduct;
import cn.anselyuki.service.ApplyProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@Tag(name = "ApplyProduct", description = "物资申请相关接口")
@RequestMapping("order")
@RestController
@RequiredArgsConstructor
public class ApplyProductController {
    private final ApplyProductRepository applyProductRepository;
    private final ApplyProductService applyProductService;

    @PutMapping("add")
    @Operation(summary = "物资申请", description = "物资申请，UUID由内部生成并返回")
    public ResponseEntity<Result<ApplyProduct>> addOrder(@RequestBody ApplyProduct applyProduct) {
        try {
            applyProductRepository.save(applyProduct);
        } catch (Exception exception) {
            exception.printStackTrace();
            return Result.fail(500, "系统内部失败,请检查日志");
        }
        return Result.success(applyProduct);
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "删除入库单", description = "删除入库单")
    public ResponseEntity<Result<ApplyProduct>> deleteInStock(@PathVariable String id) {
        if (!applyProductRepository.existsById(id)) return Result.fail(404, "物资不存在");
        try {
            applyProductRepository.deleteById(id);
        } catch (Exception e) {
            Result.fail(403, "删除物资失败");
        }
        return Result.success(null);
    }

    @PatchMapping("update")
    @Operation(summary = "更新入库单", description = "更新入库单")
    public ResponseEntity<Result<ApplyProduct>> updateInStock(@RequestBody ApplyProduct applyProduct) {
        if (applyProduct.getId() == null || applyProduct.getId().isBlank()) return Result.fail(404, "物资ID不能为空");
        ApplyProduct save = applyProductRepository.findById(applyProduct.getId()).orElse(null);
        if (save != null) {
            // 通过工具类将非空属性拷贝到save中
            JpaUtils.copyNotNullProperties(applyProduct, save);
        } else {
            return Result.fail(404, "该物资不存在");
        }
        try {
            save.setModifiedTime(new Date());
            applyProductRepository.save(save);
        } catch (Exception e) {
            return Result.fail(403, "更新物资失败");
        }
        return Result.success(save);
    }

    @GetMapping("list")
    @Operation(summary = "获取入库单列表", description = "获取入库单列表")
    public ResponseEntity<Result<List<ApplyProduct>>> listInStock() {
        return Result.success(applyProductRepository.findAll());
    }

    @GetMapping("query/{id}")
    @Operation(summary = "查询入库单分类", description = "查询入库单分类")
    public ResponseEntity<Result<ApplyProduct>> queryInStock(@PathVariable String id) {
        ApplyProduct applyProduct = applyProductRepository.findById(id).orElse(null);
        return Result.success(applyProduct);
    }

    @PostMapping("apply")
    @Operation(summary = "申请物资", description = "申请物资")
    public ResponseEntity<Result<ApplyInfoVO>> apply(@RequestBody ApplyInfoDTO applyInfoDTO) {
        ApplyInfoVO applyInfoVO = applyProductService.apply(applyInfoDTO);
        return Result.success(applyInfoVO);
    }

    @PostMapping("validation")
    @Operation(summary = "验证物资", description = "验证物资")
    public ResponseEntity<Result<ApplyInfoVO>> validation(@RequestBody ApplyInfoDTO applyInfoDTO) {
        ApplyInfoVO applyInfoVO = applyProductService.validation(applyInfoDTO);
        return Result.success(applyInfoVO);
    }
}