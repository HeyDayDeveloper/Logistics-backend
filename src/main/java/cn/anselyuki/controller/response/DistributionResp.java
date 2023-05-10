package cn.anselyuki.controller.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DistributionResp {
    private String id;
    private String pid;
    private String address;
    private String remark;
    private List<Distribution> distributions;
}
