package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.chatmessage.*;
import org.senla.woodygray.dtos.mapper.ChatMessageMapper;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.model.ChatMessage;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.ChatMessageRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final UserService userService;

    @Transactional
    public List<ChatMessageGetResponse> getAll(@Valid ChatMessageGetRequest chatMessageDto) {

        List<ChatMessage> chatMessages = chatMessageRepository.findAllByIdSenderIdOpponent(
                chatMessageDto.idSender(),
                chatMessageDto.idOpponent()
        );

        return chatMessageMapper.toChatMessageGetResponse(chatMessages);
    }

    @Transactional
    public ChatMessageCreateResponse create(@Valid ChatMessageCreateRequest chatMessageDto) throws UserNotFoundException {
        User sender = userService.findById(chatMessageDto.idSender());
        User opponent = userService.findById(chatMessageDto.idOpponent());

        ChatMessage chatMessage = chatMessageMapper.toChatMessage(chatMessageDto);
        chatMessage.setSender(sender);
        chatMessage.setOpponent(opponent);
        //TODO: прокидывать пользователей?

        chatMessageRepository.save(chatMessage);

        return chatMessageMapper.toChatMessageCreateResponse(chatMessage);
    }


    @Transactional
    public void delete(ChatMessageDeleteRequest chatMessageDeleteDto, Long id) {
        //TODO: проверка на сендера
        chatMessageRepository.deleteById(id);
    }
}
