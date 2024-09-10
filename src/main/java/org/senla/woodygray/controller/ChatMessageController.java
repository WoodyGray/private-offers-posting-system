package org.senla.woodygray.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.chatmessage.*;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.model.ChatMessage;
import org.senla.woodygray.service.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat-message")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/{idOpponent}")
    public ResponseEntity<List<ChatMessageGetResponse>> getChatMessages(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable Long idOpponent
    ) {
        return ResponseEntity.ok(chatMessageService.getAll(token.substring(7), idOpponent));
        //TODO: можно получить только свои переписки
    }


    @PostMapping
    public ResponseEntity<ChatMessageCreateResponse> createChatMessage(
            @RequestBody ChatMessageCreateRequest chatMessageDto,
            @RequestHeader(value = "Authorization") String token
    ) throws UserNotFoundException {
        return ResponseEntity.ok(chatMessageService.create(chatMessageDto, token.substring(7)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChatMessage(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization") String token
    ){
        chatMessageService.delete(token, id);
        return ResponseEntity.ok("Successfully deleted chat message");
        //TODO: удалять можно только свои сообщения
    }

}
