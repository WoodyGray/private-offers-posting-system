package org.senla.woodygray.repository.impl;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.model.ChatMessage;
import org.senla.woodygray.model.Comment;
import org.senla.woodygray.model.Offer;
import org.senla.woodygray.repository.ChatMessageRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final EntityManager em;

    @Override
    public List<ChatMessage> findAllByIdSenderIdOpponent(Long senderId, Long opponentId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ChatMessage> cq = cb.createQuery(ChatMessage.class);

        Root<ChatMessage> chatMessageRoot = cq.from(ChatMessage.class);

        cq.select(chatMessageRoot);

        Predicate titlePredicate = cb.equal(chatMessageRoot.get("sender").get("id"), senderId);
        Predicate descriptionPredicate = cb.equal(chatMessageRoot.get("opponent").get("id"), opponentId);

        Predicate titlePredicateRev = cb.equal(chatMessageRoot.get("sender").get("id"), opponentId);
        Predicate descriptionPredicateRev = cb.equal(chatMessageRoot.get("opponent").get("id"), senderId);


        cq.where(cb.or(cb.and(titlePredicate, descriptionPredicate),
                cb.and(titlePredicateRev, descriptionPredicateRev)));


        List<ChatMessage> offers = em.createQuery(cq).getResultList();

        return offers;
    }

    @Override
    public void save(ChatMessage chatMessage) {
        em.persist(chatMessage);
    }

    @Override
    public void deleteById(Long id) {
        try {
            ChatMessage chatMessage = em.find(ChatMessage.class, id);
            em.remove(chatMessage);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
