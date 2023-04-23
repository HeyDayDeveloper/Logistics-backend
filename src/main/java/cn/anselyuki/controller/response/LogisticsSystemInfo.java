package cn.anselyuki.controller.response;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author AnselYuki
 * @date 2023/4/23 16:10
 */
@Data
@ConfigurationProperties("info")
public class LogisticsSystemInfo {
    private String title;
    private String description;
    private String version;
}
