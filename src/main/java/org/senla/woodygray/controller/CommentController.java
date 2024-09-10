package org.senla.woodygray.controller;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.comment.CommentCreateRequest;
import org.senla.woodygray.dtos.comment.CommentCreateResponse;
import org.senla.woodygray.dtos.comment.CommentGetResponse;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("{idOffer}")
    public ResponseEntity<List<CommentGetResponse>> getComment(@PathVariable Long idOffer) {
        return ResponseEntity.ok(commentService.getAllOfferComments(idOffer));
    }

    @PostMapping("{idOffer}")
    public ResponseEntity<CommentCreateResponse> createComment(
            @RequestBody CommentCreateRequest commentCreateRequest,
            @PathVariable Long idOffer) throws UserNotFoundException {
        return ResponseEntity.ok(commentService.createCommentForOffer(commentCreateRequest, idOffer));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteComment(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable Long id) {
        commentService.deleteById(token.substring(7), id);
        return ResponseEntity.ok("Successfully deleted comment");
        //TODO: можно удалить только свой коммент
    }
}
