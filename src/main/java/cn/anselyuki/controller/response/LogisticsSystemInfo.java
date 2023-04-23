package cn.anselyuki.controller.response;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author AnselYuki
 */
@Data
@ConfigurationProperties("info")
public class LogisticsSystemInfo {
    private String title;
    private String description;
    private String version;
}
