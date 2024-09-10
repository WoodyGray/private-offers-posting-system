package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.comment.CommentCreateRequest;
import org.senla.woodygray.dtos.comment.CommentCreateResponse;
import org.senla.woodygray.dtos.comment.CommentGetResponse;
import org.senla.woodygray.dtos.mapper.CommentMapper;
import org.senla.woodygray.exceptions.HostException;
import org.senla.woodygray.exceptions.OfferNotFoundException;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.model.Comment;
import org.senla.woodygray.model.Offer;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.CommentRepository;
import org.senla.woodygray.util.JwtTokenUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final OfferService offerService;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    @Transactional
    public List<CommentGetResponse> getAllOfferComments(Long idOffer) {
        if (offerService.findById(idOffer) == null) {
            throw new OfferNotFoundException("offer with id " + idOffer + " not found");
        }

        List<Comment> comments = commentRepository.findAllByOfferId(idOffer);

        return commentMapper.toCommentGetResponseList(comments);
    }


    public CommentCreateResponse createCommentForOffer(CommentCreateRequest commentCreateRequest, Long idOffer) throws UserNotFoundException {
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

    public void deleteById(String token, Long id) {
        //TODO: проверка пользователя
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()){
            throw new NoResultException("Can't find comment with id " + id);
        }
        Comment comment = optionalComment.get();
        if (!comment.getSender().getId().equals(jwtTokenUtils.getUserId(token))){
            throw new HostException("Only host can delete his comment");
        }
        commentRepository.deleteById(id);
    }
}
