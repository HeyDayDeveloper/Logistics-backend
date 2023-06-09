package cn.anselyuki.controller;

import cn.anselyuki.common.utils.JpaUtils;
import cn.anselyuki.controller.response.Result;
import cn.anselyuki.repository.ExpressInfoRepository;
import cn.anselyuki.repository.entity.ExpressInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@Tag(name = "ExpressInfo", description = "物流订单相关接口")
@RequestMapping("express")
@RestController
@RequiredArgsConstructor
public class ExpressInfoController {
    private final ExpressInfoRepository expressInfoRepository;

    @PutMapping("add")
    @Operation(summary = "添加物流订单", description = "添加物流订单，UUID由内部生成并返回")
    public ResponseEntity<Result<ExpressInfo>> add(@RequestBody ExpressInfo expressInfo) {
        try {
            expressInfo.setCreateTime(new Date());
            expressInfoRepository.save(expressInfo);
        } catch (Exception exception) {
            exception.printStackTrace();
            return Result.fail(500, "系统内部失败,请检查日志");
        }
        return Result.success(expressInfo);
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "删除物流订单", description = "删除物流订单")
    public ResponseEntity<Result<Void>> delete(@PathVariable String id) {
        if (!expressInfoRepository.existsById(id)) return Result.fail(404, "物流订单不存在");
        try {
            expressInfoRepository.deleteById(id);
        } catch (Exception e) {
            return Result.fail(403, "删除物流订单失败");
        }
        return Result.success(null);
    }

    @PatchMapping("update")
    @Operation(summary = "更新物流订单", description = "更新物流订单")
    public ResponseEntity<Result<ExpressInfo>> update(@RequestBody ExpressInfo expressInfo) {
        if (expressInfo.getId() == null || expressInfo.getId().isBlank()) return Result.fail(404, "物资ID不能为空");
        ExpressInfo save = expressInfoRepository.findById(expressInfo.getId()).orElse(null);
        if (save != null) {
            // 通过工具类将非空属性拷贝到save中
            JpaUtils.copyNotNullProperties(expressInfo, save);
        } else {
            return Result.fail(404, "该物流订单不存在");
        }
        try {
            save.setModifiedTime(new Date());
            expressInfoRepository.save(save);
        } catch (Exception e) {
            return Result.fail(403, "更新物流订单失败");
        }
        return Result.success(save);
    }

    @GetMapping("list")
    @Operation(summary = "获取物流订单列表", description = "获取物流订单列表")
    public ResponseEntity<Result<List<ExpressInfo>>> list() {
        return Result.success(expressInfoRepository.findAll());
    }

    @GetMapping("query/{id}")
    @Operation(summary = "查询物流订单", description = "根据ID查询物流订单")
    public ResponseEntity<Result<ExpressInfo>> query(@PathVariable String id) {
        ExpressInfo expressInfo = expressInfoRepository.findById(id).orElse(null);
        return Result.success(expressInfo);
    }
}