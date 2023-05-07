package cn.anselyuki.controller.response;

import lombok.Data;

@Data
public class ApplyInfoVO {
    private String id;
    private String productName;
    private Integer degree;
    private String address;
    private Integer num;
    private Boolean status;
    private UserInfoVO userInfo;
}
