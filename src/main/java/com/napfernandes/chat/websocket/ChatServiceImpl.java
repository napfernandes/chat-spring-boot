package com.napfernandes.chat.websocket;

import com.napfernandes.chat.conversation.ConversationService;
import com.napfernandes.chat.conversation.dto.message.ConversationMessageInput;
import com.napfernandes.chat.conversation.dto.message.ConversationMessageOutput;
import com.napfernandes.chat.conversation.dto.message.action.MessageActionOutput;
import com.napfernandes.chat.conversation.enums.MessageActionType;
import com.napfernandes.chat.conversation.exception.ConversationNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
        private final ConversationService conversationService;

        public ChatServiceImpl(ConversationService conversationService) {
                this.conversationService = conversationService;
        }

        @Override
        public WebSocketMessage sendMessageToConversation(String conversationId, WebSocketMessage message)
                        throws ConversationNotFoundException {

                ConversationMessageInput messageInput = new ConversationMessageInput(message.getFrom(),
                                message.getContent());

                ConversationMessageOutput messageOutput = this.conversationService.insertMessageToConversation(
                                conversationId,
                                messageInput);

                MessageActionOutput actionOfMessageSent = messageOutput.getActions().stream()
                                .filter(a -> a.getActionType() == MessageActionType.MessageSent)
                                .findFirst()
                                .get();

                return new WebSocketMessage(messageOutput.getUserId(), messageOutput.getMessage(),
                                actionOfMessageSent.getCreatedAt());
        }
}
