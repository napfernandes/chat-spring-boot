package com.napfernandes.chat.conversation;

import com.napfernandes.chat.conversation.entity.ConversationMessage;
import com.napfernandes.chat.conversation.entity.MessageAction;

public interface ConversationRepositoryCustom {
    ConversationMessage insertMessageToConversation(String conversationId, ConversationMessage message);

    MessageAction insertActionToMessage(String conversationId, String messageId, MessageAction action);
}
