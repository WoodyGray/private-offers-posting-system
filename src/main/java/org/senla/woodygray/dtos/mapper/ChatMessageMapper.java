package org.senla.woodygray.dtos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.senla.woodygray.dtos.chatmessage.ChatMessageCreateRequest;
import org.senla.woodygray.dtos.chatmessage.ChatMessageCreateResponse;
import org.senla.woodygray.dtos.chatmessage.ChatMessageGetResponse;
import org.senla.woodygray.model.ChatMessage;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChatMessageMapper {

    @Mapping(target = "idSender", source = "sender.id")
    @Mapping(target = "message", source = "messageText")
    ChatMessageGetResponse toChatMessageGetResponse(ChatMessage chatMessage);

    List<ChatMessageGetResponse> toChatMessageGetResponse(List<ChatMessage> chatMessages);

    @Mapping(target = "messageText", source = "message")
    ChatMessage toChatMessage(ChatMessageCreateRequest chatMessageDto);

    @Mapping(target = "message", source = "messageText")
    ChatMessageCreateResponse toChatMessageCreateResponse(ChatMessage chatMessage);
}
