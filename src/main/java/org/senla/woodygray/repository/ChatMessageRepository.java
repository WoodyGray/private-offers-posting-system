package org.senla.woodygray.repository;

import org.senla.woodygray.model.ChatMessage;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ChatMessageRepository {
    List<ChatMessage> findAllByIdSenderIdOpponent( Long senderId, Long opponentId );

    void save(ChatMessage chatMessage);

    void deleteById(Long id);
}
