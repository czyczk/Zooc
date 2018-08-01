package com.zzzz.controller;

import com.zzzz.dto.PromotionStrategyParam;
import com.zzzz.po.PromotionStrategy;
import com.zzzz.service.PromotionStrategyService;
import com.zzzz.service.PromotionStrategyServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/api/v1")
public class PromotionStrategyController {
    private final PromotionStrategyService promotionStrategyService;

    @Autowired
    public PromotionStrategyController(PromotionStrategyService promotionStrategyService) {
        this.promotionStrategyService = promotionStrategyService;
    }

    /**
     * Update a promotion strategy. Fields will be left unchanged if not specified.
     * @param enterpriseId Enterprise ID
     * @param param useCoupons, usePoints, pointsPerYuan
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping("/promotion-strategy/{id}")
    public ResponseEntity update(@PathVariable("id") String enterpriseId,
                                 @RequestBody PromotionStrategyParam param) throws PromotionStrategyServiceException, SQLException {
        promotionStrategyService.update(enterpriseId, param.getUseCoupons(), param.getUsePoints(), param.getPointsPerYuan());
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a promotion strategy.
     * @param enterpriseId Enterprise ID
     * @return Success: Promotion strategy; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/promotion-strategy/{id}")
    public ResponseEntity<PromotionStrategy> get(@PathVariable("id") String enterpriseId) throws PromotionStrategyServiceException, SQLException {
        PromotionStrategy result = promotionStrategyService.getByEnterpriseId(enterpriseId);
        return ResponseEntity.ok(result);
    }
}
