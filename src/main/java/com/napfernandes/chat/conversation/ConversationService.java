package com.napfernandes.chat.conversation;

import java.util.List;

import com.napfernandes.chat.conversation.dto.ConversationInput;
import com.napfernandes.chat.conversation.dto.ConversationOutput;
import com.napfernandes.chat.conversation.dto.message.ConversationMessageInput;
import com.napfernandes.chat.conversation.dto.message.ConversationMessageOutput;
import com.napfernandes.chat.conversation.dto.message.action.MessageActionInput;
import com.napfernandes.chat.conversation.dto.message.action.MessageActionOutput;
import com.napfernandes.chat.conversation.exception.ConversationNotFoundException;

public interface ConversationService {
        List<ConversationOutput> findAllConversations();

        ConversationOutput getConversationByIdOrHash(String conversationIdOrHash) throws ConversationNotFoundException;

        ConversationOutput insertConversation(ConversationInput input);

        ConversationMessageOutput insertMessageToConversation(String conversationIdOrHash,
                        ConversationMessageInput input)
                        throws ConversationNotFoundException;

        MessageActionOutput insertActionToMessage(String conversationIdOrHash, String messageId,
                        MessageActionInput input)
                        throws ConversationNotFoundException;
}