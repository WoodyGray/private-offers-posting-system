package org.senla.woodygray.controller;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.offer.PromotionResponse;
import org.senla.woodygray.service.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotion")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @PostMapping("/{offerId}")
    public ResponseEntity<PromotionResponse> promoteOffer(
            @PathVariable Long offerId) {
        return ResponseEntity.ok(promotionService.promoteOffer(offerId));
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<PromotionResponse> getPromotionInfoFromOffer(@PathVariable Long offerId){
        return ResponseEntity.ok(promotionService.getInfo(offerId));
    }

}
