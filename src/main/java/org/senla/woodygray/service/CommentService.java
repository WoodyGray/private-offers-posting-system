package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.comment.CommentCreateRequest;
import org.senla.woodygray.dtos.comment.CommentCreateResponse;
import org.senla.woodygray.dtos.comment.CommentGetResponse;
import org.senla.woodygray.dtos.mapper.CommentMapper;
import org.senla.woodygray.exceptions.OfferNotFoundException;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.model.Comment;
import org.senla.woodygray.model.Offer;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.CommentRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final OfferService offerService;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @Transactional
    public List<CommentGetResponse> getAllOfferComments(Long idOffer) {
        if (offerService.findById(idOffer) == null) {
            throw new OfferNotFoundException("offer with id " + idOffer + " not found");
        }

        List<Comment> comments = commentRepository.findAllByOfferId(idOffer);

        return commentMapper.toCommentGetResponseList(comments);
    }


    public CommentCreateResponse createCommentForOffer(CommentCreateRequest commentCreateRequest, Long idOffer) throws UserNotFoundException {
        if (commentCreateRequest.idSender() == null
        || commentCreateRequest.sentAt() == null){
            throw new BadCredentialsException("id sender or sent at is null");
        }
        User user = userService.findById(commentCreateRequest.idSender());
        Offer offer = offerService.findById(idOffer);
        if (offer == null) {
            throw new OfferNotFoundException("offer with id " + idOffer + " not found");
        }
        Comment comment = commentMapper.toComment(commentCreateRequest);
        comment.setOffer(offer);
        comment.setSender(user);

        commentRepository.save(comment);

        return commentMapper.toCommentCreateResponse(comment);
    }

    public void deleteById(Long id) {
        //TODO: проверка пользователя
        commentRepository.deleteById(id);
    }
}
