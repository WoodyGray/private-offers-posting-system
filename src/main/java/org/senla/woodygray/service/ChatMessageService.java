package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.chatmessage.ChatMessageCreateRequest;
import org.senla.woodygray.dtos.chatmessage.ChatMessageCreateResponse;
import org.senla.woodygray.dtos.chatmessage.ChatMessageGetRequest;
import org.senla.woodygray.dtos.chatmessage.ChatMessageGetResponse;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.model.ChatMessage;
import org.senla.woodygray.model.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final UserService userService;

    public List<ChatMessageGetResponse> getAll(ChatMessageGetRequest chatMessageDto) {
        if (chatMessageDto.idOpponent() == null
        || chatMessageDto.idSender() == null) {
            throw new BadCredentialsException("id opponent or sender is null");
        }//TODO: use
        //@NotNull(message = "idOpponent must not be null") Long idOpponent,
        //getAll(@Valid ChatMessageGetRequest chatMessageDto)

        List<ChatMessage> chatMessages = chatMessageRepository.findAllByIdSenderIdOpponent(
                chatMessageDto.idSender(),
                chatMessageDto.idOpponent()
        );

        return chatMessageMapper.toChatMessageGetResponse(chatMessages);
    }

    public ChatMessageCreateResponse create(ChatMessageCreateRequest chatMessageDto) throws UserNotFoundException {

        User sender = userService.findById(chatMessageDto.idSender());
        User opponent = userService.findById(chatMessageDto.idOpponent());


    }
}
