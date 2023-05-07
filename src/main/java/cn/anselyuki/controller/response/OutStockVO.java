package cn.anselyuki.controller.response;

import cn.anselyuki.repository.entity.Product;
import lombok.Data;

import java.util.Date;

@Data
public class OutStockVO {
    private String id;
    private String pid;
    private String uid;
    private String address;
    private Integer num;
    private String remark;
    private Date createTime;
    private Product product;
    private UserInfoVO userInfo;
}
