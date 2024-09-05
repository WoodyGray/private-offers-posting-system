package org.senla.woodygray.dtos.mapper;

import org.mapstruct.*;
import org.senla.woodygray.dtos.comment.CommentCreateRequest;
import org.senla.woodygray.dtos.comment.CommentCreateResponse;
import org.senla.woodygray.dtos.comment.CommentGetResponse;
import org.senla.woodygray.model.Comment;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "messageText", target = "message")
    CommentGetResponse toCommentGetResponse(Comment comment);

    @Mapping(source = "message", target = "messageText")
    Comment toComment(CommentCreateRequest commentCreateRequest);

    @Mapping(source = "messageText", target = "message")
    CommentCreateResponse toCommentCreateResponse(Comment comment);

    List<CommentGetResponse> toCommentGetResponseList(List<Comment> comments);


}
