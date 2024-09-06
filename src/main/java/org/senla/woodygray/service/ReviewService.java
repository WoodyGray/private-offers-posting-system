package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.ReviewCreateRequest;
import org.senla.woodygray.dtos.ReviewCreateResponse;
import org.senla.woodygray.model.Review;
import org.senla.woodygray.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserService userService;

    public ReviewCreateResponse createReview(Long idSeller, ReviewCreateRequest reviewCreateRequest, String token) {
        User seller = userService.findById(idSeller);
        User sender = userService.findByToken(token);

        Review review = reviewMapper.toReview(reviewCreateRequest, seller, sender);

        reviewRepository.save(review);
        Long cntOfReview = review.getAllByUserId().size();
        seller.setRating((seller.getRating() * cntOfReview + review.getGrade())/(cntOfReview+1));
    }
}
