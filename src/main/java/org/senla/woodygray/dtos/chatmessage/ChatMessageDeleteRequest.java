package org.senla.woodygray.dtos.chatmessage;

import javax.validation.constraints.NotNull;

public record ChatMessageDeleteRequest(
        @NotNull(message = "id sender can't be null") Long idSender
) {
}
