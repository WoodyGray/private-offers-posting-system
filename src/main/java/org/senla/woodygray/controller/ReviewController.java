package org.senla.woodygray.controller;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.ReviewCreateRequest;
import org.senla.woodygray.dtos.ReviewCreateResponse;
import org.senla.woodygray.dtos.ReviewGetResponse;
import org.senla.woodygray.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<ReviewCreateResponse> createReview(
            @RequestBody ReviewCreateRequest reviewCreateRequest,
            @RequestHeader(value = "Authorization") String token
    ){
        return ResponseEntity.ok(
                reviewService.createReview(reviewCreateRequest, token.substring(7)));
    }

    @GetMapping("/review/{userId}")
    public ResponseEntity<List<ReviewGetResponse>> getReviewsFromUser(
            @PathVariable Long userId
    ){
        return ResponseEntity.ok(
                reviewService.getReviewsFromUser(userId)
        );
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization") String token
    ){
        reviewService.deleteReview(id, token.substring(7));
        return ResponseEntity.ok("Deleted successfully");
    }

}
