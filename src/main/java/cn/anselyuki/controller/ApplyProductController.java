package cn.anselyuki.controller;

import cn.anselyuki.common.utils.JpaUtils;
import cn.anselyuki.controller.request.ApplyInfoDTO;
import cn.anselyuki.controller.request.ValidationDTO;
import cn.anselyuki.controller.response.ApplyInfoVO;
import cn.anselyuki.controller.response.OutStockVO;
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

    @DeleteMapping("delete/{id}")
    @Operation(summary = "删除入库单", description = "删除入库单")
    public ResponseEntity<Result<ApplyProduct>> deleteInStock(@PathVariable String id) {
        if (!applyProductRepository.existsById(id)) return Result.fail(404, "物流订单不存在");
        try {
            applyProductRepository.deleteById(id);
        } catch (Exception e) {
            return Result.fail(403, "删除物流订单失败");
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
        List<ApplyProduct> applyProductList = applyProductService.getListByDegree();
        return Result.success(applyProductList);
    }

    @GetMapping("query/{id}")
    @Operation(summary = "查询入库单", description = "查询入库单")
    public ResponseEntity<Result<ApplyProduct>> queryInStock(@PathVariable String id) {
        ApplyProduct applyProduct = applyProductRepository.findById(id).orElse(null);
        return Result.success(applyProduct);
    }

    @PostMapping("apply")
    @Operation(summary = "申请物资", description = "申请物资")
    public ResponseEntity<Result<ApplyInfoVO>> apply(@RequestBody ApplyInfoDTO applyInfoDTO) {
        log.info("申请物资：{}", applyInfoDTO);
        ApplyInfoVO applyInfoVO = applyProductService.apply(applyInfoDTO);
        return Result.success(applyInfoVO);
    }

    @PostMapping("validation")
    @Operation(summary = "验证物资", description = "验证物资")
    public ResponseEntity<Result<OutStockVO>> validation(@RequestBody ValidationDTO validationDTO) {
        String id = validationDTO.getId();
        String pid = validationDTO.getPid();
        String remark = validationDTO.getRemark();
        return applyProductService.validation(id, pid, remark);
    }
}