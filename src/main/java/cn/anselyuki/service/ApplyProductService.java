package cn.anselyuki.service;

import cn.anselyuki.controller.request.ApplyInfoDTO;
import cn.anselyuki.controller.response.ApplyInfoVO;
import cn.anselyuki.controller.response.Result;
import cn.anselyuki.repository.entity.ApplyProduct;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplyProductService {
    ApplyInfoVO apply(ApplyInfoDTO applyInfoDTO);

    ResponseEntity<Result<Object>> validation(String id, String pid);

    List<ApplyProduct> getListByDegree();
}
