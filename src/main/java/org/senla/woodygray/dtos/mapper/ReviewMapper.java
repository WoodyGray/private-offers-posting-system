package org.senla.woodygray.dtos.mapper;

import org.mapstruct.*;
import org.senla.woodygray.dtos.ReviewCreateRequest;
import org.senla.woodygray.dtos.ReviewCreateResponse;
import org.senla.woodygray.dtos.ReviewGetResponse;
import org.senla.woodygray.model.Review;
import org.senla.woodygray.model.User;

import javax.validation.Valid;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {

    @Mapping(source = "reviewCreateRequest.message", target = "messageText")
    @Mapping(source = "reviewCreateRequest.grade", target = "grade")
    @Mapping(source = "reviewCreateRequest.sentAt", target = "sentAt")
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "sender", source = "sender")
    @Mapping(target = "id", ignore = true)
    Review toReview(@Valid ReviewCreateRequest reviewCreateRequest, User seller, User sender);

    @Mapping(source = "messageText", target = "message")
    ReviewCreateResponse toReviewCreateResponse(Review review);

    @Mapping(source = "sender.id", target = "idSender")
    @Mapping(source = "messageText", target = "message")
    ReviewGetResponse toReviewGetResponse(Review review);

    List<ReviewGetResponse> toReviewGetResponseList(List<Review> reviews);
}
