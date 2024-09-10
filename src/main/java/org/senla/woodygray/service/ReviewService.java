package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.ReviewCreateRequest;
import org.senla.woodygray.dtos.ReviewCreateResponse;
import org.senla.woodygray.dtos.ReviewGetResponse;
import org.senla.woodygray.dtos.mapper.ReviewMapper;
import org.senla.woodygray.exceptions.ReviewDeleteException;
import org.senla.woodygray.exceptions.ReviewNotFoundException;
import org.senla.woodygray.model.Review;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.ReviewRepository;
import org.senla.woodygray.util.JwtTokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserService userService;
    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;
    private final JwtTokenUtils jwtTokenUtils;

    @Transactional
    public ReviewCreateResponse createReview(@Valid ReviewCreateRequest reviewCreateRequest, String token) {
        User seller = userService.findById(reviewCreateRequest.idSeller());
        User sender = userService.findByToken(token);

        Review review = reviewMapper.toReview(reviewCreateRequest, seller, sender);

        reviewRepository.save(review);
        long cntOfReview = reviewRepository.getAllByUserId(seller.getId()).size();
        seller.setRating((seller.getRating() * cntOfReview + review.getGrade())/(cntOfReview+1));

        userService.update(seller);
        return reviewMapper.toReviewCreateResponse(review);
    }

    @Transactional
    public List<ReviewGetResponse> getReviewsFromUser(Long userId) {
        List<Review> reviews = reviewRepository.getAllByUserId(userId);
        return reviewMapper.toReviewGetResponseList(reviews);
    }

    @Transactional
    public void deleteReview(Long id, String token) {
        Optional<Review> reviewOptional = reviewRepository.getById(id);
        if (reviewOptional.isEmpty()) {
            throw new ReviewNotFoundException("can't find review with id " + id);
        }

        Review review = reviewOptional.get();
        User user = userService.findByToken(token);
        if (!user.getId().equals(review.getSender().getId())) {
            throw new ReviewDeleteException("only host can delete his review");
        }

        long cntOfReview = reviewRepository.getAllByUserId(user.getId()).size();
        user.setRating((user.getRating()*cntOfReview - review.getGrade())/cntOfReview-1);
        reviewRepository.delete(review);
        userService.update(user);
    }
}
