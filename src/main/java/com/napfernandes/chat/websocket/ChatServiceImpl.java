package com.napfernandes.chat.websocket;

import com.napfernandes.chat.conversation.ConversationService;
import com.napfernandes.chat.conversation.dto.message.ConversationMessageInput;
import com.napfernandes.chat.conversation.enums.MessageActionType;
import com.napfernandes.chat.conversation.exception.ConversationNotFoundException;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
        @Autowired
        private ConversationService conversationService;

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        @Override
        public WebSocketMessage sendMessageToConversation(String conversationId, WebSocketMessage message)
                        throws ConversationNotFoundException {

                var messageInput = ConversationMessageInput.builder()
                                .userId(message.getFrom())
                                .message(message.getContent())
                                .build();

                var messageOutput = this.conversationService.insertMessageToConversation(
                                conversationId,
                                messageInput);

                var actionOfMessageSent = messageOutput.getActions().stream()
                                .filter(a -> a.getActionType() == MessageActionType.MessageSent)
                                .findFirst()
                                .get();

                return WebSocketMessage.builder()
                                .from(messageOutput.getUserId())
                                .content(messageOutput.getMessage())
                                .createdAt(actionOfMessageSent.getCreatedAt())
                                .build();
        }
}
