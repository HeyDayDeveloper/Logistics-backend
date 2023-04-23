package cn.anselyuki.controller;

import cn.anselyuki.controller.response.LogisticsSystemInfo;
import cn.anselyuki.controller.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AnselYuki
 */
@Tag(name = "System")
@RequestMapping("")
@RestController
@RequiredArgsConstructor
public class SystemController {
    private final LogisticsSystemInfo systemInfo;

    @GetMapping("/")
    @Operation(summary = "测试API")
    public ResponseEntity<Result<String>> test() {
        return Result.success("Logistics Backend Server is Running", null);
    }

    @GetMapping("version")
    @Operation(summary = "获取API当前版本")
    public ResponseEntity<Result<LogisticsSystemInfo>> getSystemVersion() {
        return Result.success(systemInfo);
    }
}
