package cn.anselyuki.controller.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class Distribution {
    //节点id
    private String id;
    //距离
    private Float distance;
    //人口数量
    private Integer peopleNum;
    //需求数量
    private Integer applyNum;
    private Date createTime;
}
