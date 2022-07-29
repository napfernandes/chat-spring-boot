package com.napfernandes.chat.conversation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.napfernandes.chat.conversation.dto.ConversationInput;
import com.napfernandes.chat.conversation.dto.ConversationOutput;
import com.napfernandes.chat.conversation.dto.message.ConversationMessageOutput;
import com.napfernandes.chat.conversation.dto.message.action.MessageActionInput;
import com.napfernandes.chat.conversation.dto.message.action.MessageActionOutput;
import com.napfernandes.chat.conversation.exception.ConversationNotFoundException;
import com.napfernandes.chat.conversation.dto.message.ConversationMessageInput;

@RestController
public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping("/conversations")
    public List<ConversationOutput> findAllConversations() {
        return this.conversationService.findAllConversations();
    }

    @PostMapping("/conversations")
    public ConversationOutput insertConversation(@RequestBody ConversationInput conversationInput) {
        return this.conversationService.insertConversation(conversationInput);
    }

    @GetMapping("/conversations/{conversationIdOrHash}")
    public ConversationOutput getConversationByIdOrHash(
            @PathVariable("conversationIdOrHash") String conversationIdOrHash)
            throws ConversationNotFoundException {
        return this.conversationService.getConversationByIdOrHash(conversationIdOrHash);
    }

    @PostMapping("/conversations/{conversationIdOrHash}/messages")
    public ConversationMessageOutput insertMessageToConversation(
            @PathVariable("conversationIdOrHash") String conversationIdOrHash,
            @RequestBody ConversationMessageInput conversationMessageInput)
            throws ConversationNotFoundException {
        return this.conversationService.insertMessageToConversation(conversationIdOrHash, conversationMessageInput);
    }

    @PostMapping("/conversations/{conversationIdOrHash}/messages/{messageId}/actions")
    public MessageActionOutput insertActionToMessage(
            @PathVariable("conversationIdOrHash") String conversationIdOrHash,
            @PathVariable("messageId") String messageId,
            @RequestBody MessageActionInput messageActionInput)
            throws ConversationNotFoundException {
        return this.conversationService.insertActionToMessage(conversationIdOrHash, messageId, messageActionInput);
    }

}
