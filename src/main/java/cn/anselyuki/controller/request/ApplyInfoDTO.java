package cn.anselyuki.controller.request;

import lombok.Data;

@Data
public class ApplyInfoDTO {
    //申请人ID
    private String uid;
    //申请物资名称，不一定存在
    private String pid;
    private Integer degree;
    private String address;
    private Integer num;
}
