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

    @GetMapping
    public ResponseEntity<List<ChatMessageGetResponse>> getChatMessages(
            @RequestBody ChatMessageGetRequest chatMessageDto
    ) {
        return ResponseEntity.ok(chatMessageService.getAll(chatMessageDto));
    }


    @PostMapping
    public ResponseEntity<ChatMessageCreateResponse> createChatMessage(
            @RequestBody ChatMessageCreateRequest chatMessageDto
    ) throws UserNotFoundException {
        return ResponseEntity.ok(chatMessageService.create(chatMessageDto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChatMessage(
            @PathVariable Long id,
            @RequestBody ChatMessageDeleteRequest chatMessageDeleteDto
    ){
        chatMessageService.delete(chatMessageDeleteDto, id);
        return ResponseEntity.ok("Successfully deleted chat message");
    }

}
