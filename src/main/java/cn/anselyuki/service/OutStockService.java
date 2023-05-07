package cn.anselyuki.service;

import cn.anselyuki.controller.response.OutStockVO;

import java.util.List;

public interface OutStockService {
    List<OutStockVO> listForOutStock();

    OutStockVO queryForOutStock(String id);
}
