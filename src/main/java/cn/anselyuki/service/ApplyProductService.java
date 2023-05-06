package cn.anselyuki.service;

import cn.anselyuki.controller.request.ApplyInfoDTO;
import cn.anselyuki.controller.response.ApplyInfoVO;
import cn.anselyuki.controller.response.Result;
import org.springframework.http.ResponseEntity;

public interface ApplyProductService {
    ApplyInfoVO apply(ApplyInfoDTO applyInfoDTO);

    ResponseEntity<Result<Object>> validation(String id, String pid);
}
