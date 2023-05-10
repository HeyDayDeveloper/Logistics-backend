package cn.anselyuki.controller;

import cn.anselyuki.controller.response.Distribution;
import cn.anselyuki.controller.response.DistributionResp;
import cn.anselyuki.controller.response.LogisticsSystemInfo;
import cn.anselyuki.controller.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("test")
    public ResponseEntity<Result<Object>> test2() {
        DistributionResp distributionResp = new DistributionResp();
        List<Distribution> distributions = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Distribution distribution = new Distribution();
            distribution.setId(String.valueOf(UUID.randomUUID()))
                    .setDistance((float) (Math.random() * 1000))
                    .setPeopleNum((int) (Math.random() * 50) + 5)
                    .setApplyNum((int) (Math.random() * 50) + 5)
                    .setCreateTime(new Date());
            distributions.add(distribution);
        }
        distributionResp.setId(String.valueOf(UUID.randomUUID()))
                .setPid(String.valueOf(UUID.randomUUID()))
                .setAddress("长沙市长沙县安沙镇配送中心")
                .setRemark("湖南信息学院配送中心")
                .setDistributions(distributions);
        return Result.success(distributionResp);
    }
}
