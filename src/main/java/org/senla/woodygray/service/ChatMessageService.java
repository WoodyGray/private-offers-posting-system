package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.chatmessage.*;
import org.senla.woodygray.dtos.mapper.ChatMessageMapper;
import org.senla.woodygray.exceptions.HostException;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.model.ChatMessage;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.ChatMessageRepository;
import org.senla.woodygray.util.JwtTokenUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.security.auth.callback.CallbackHandler;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    @Transactional
    public List<ChatMessageGetResponse> getAll(String token, Long idOpponent) {

        List<ChatMessage> chatMessages = chatMessageRepository.findAllByIdSenderIdOpponent(
                jwtTokenUtils.getUserId(token),
                idOpponent
        );

        return chatMessageMapper.toChatMessageGetResponse(chatMessages);
    }

    @Transactional
    public ChatMessageCreateResponse create(@Valid ChatMessageCreateRequest chatMessageDto, String token) throws UserNotFoundException {
        User sender = userService.findById(jwtTokenUtils.getUserId(token));
        User opponent = userService.findById(chatMessageDto.idOpponent());

        ChatMessage chatMessage = chatMessageMapper.toChatMessage(chatMessageDto);
        chatMessage.setSender(sender);
        chatMessage.setOpponent(opponent);
        //TODO: прокидывать пользователей?

        chatMessageRepository.save(chatMessage);

        return chatMessageMapper.toChatMessageCreateResponse(chatMessage);
    }


    @Transactional
    public void delete(String token, Long id) {
        //TODO: проверка на сендера
        Optional<ChatMessage> optionalChatMessage = chatMessageRepository.findById(id);
        if (optionalChatMessage.isEmpty()){
            throw new NoResultException("Cannot find chat message with id " + id);
        }
        ChatMessage chatMessage = optionalChatMessage.get();
        if (!chatMessage.getSender().getId().equals(jwtTokenUtils.getUserId(token))){
            throw new HostException("Only host can delete his message");
        }
        chatMessageRepository.deleteById(id);
    }
}
