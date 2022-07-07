package com.napfernandes.chat.conversation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.napfernandes.chat.conversation.dto.ConversationInput;
import com.napfernandes.chat.conversation.dto.ConversationOutput;
import com.napfernandes.chat.conversation.dto.message.ConversationMessageInput;
import com.napfernandes.chat.conversation.dto.message.ConversationMessageOutput;
import com.napfernandes.chat.conversation.dto.message.action.MessageActionInput;
import com.napfernandes.chat.conversation.dto.message.action.MessageActionOutput;
import com.napfernandes.chat.conversation.entity.Conversation;
import com.napfernandes.chat.conversation.entity.ConversationMessage;
import com.napfernandes.chat.conversation.entity.MessageAction;
import com.napfernandes.chat.conversation.enums.MessageActionType;
import com.napfernandes.chat.conversation.exception.ConversationNotFoundException;
import com.napfernandes.chat.crypto.CryptoService;
import com.napfernandes.chat.service.ValidatorService;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ValidatorService<ConversationInput> conversationInputValidator;

    @Autowired
    private ValidatorService<ConversationMessageInput> messageInputValidator;

    @Autowired
    private ValidatorService<MessageActionInput> actionInputValidator;

    @Override
    public List<ConversationOutput> findAllConversations() {
        return this.conversationRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, ConversationOutput.class))
                .collect(Collectors.toList());
    }

    @Override
    public ConversationOutput getConversationByIdOrHash(String conversationIdOrHash)
            throws ConversationNotFoundException {
        Conversation conversation = this.conversationRepository.getByIdOrHash(conversationIdOrHash);

        if (conversation == null) {
            throw new ConversationNotFoundException(conversationIdOrHash);
        }

        return this.modelMapper.map(conversation, ConversationOutput.class);
    }

    @Override
    public ConversationOutput insertConversation(ConversationInput input) {
        this.conversationInputValidator.validate(input);

        Conversation conversation = this.modelMapper.map(input, Conversation.class);

        String formattedString = "conversation#%s#%s".formatted(
                String.join(",", conversation.getMembers()),
                new Date().getTime());
        String conversationHash = this.cryptoService.hashValue(formattedString, null);

        conversation.setHash(conversationHash);
        conversation.setCreatedAt(new Date());

        this.conversationRepository.insert(conversation);

        return this.modelMapper.map(conversation, ConversationOutput.class);
    }

    @Override
    public ConversationMessageOutput insertMessageToConversation(String conversationIdOrHash,
            ConversationMessageInput input) throws ConversationNotFoundException {
        this.messageInputValidator.validate(input);

        ConversationMessage message = this.modelMapper.map(input, ConversationMessage.class);
        message.addAction(new MessageAction(input.getUserId(), MessageActionType.MessageSent, new Date()));

        Conversation conversation = this.conversationRepository.getByIdOrHash(conversationIdOrHash);

        if (conversation == null) {
            throw new ConversationNotFoundException(conversationIdOrHash);
        }

        this.conversationRepository.insertMessageToConversation(conversation.getId(), message);

        return this.modelMapper.map(message, ConversationMessageOutput.class);
    }

    @Override
    public MessageActionOutput insertActionToMessage(String conversationIdOrHash, String messageId,
            MessageActionInput input) throws ConversationNotFoundException {
        this.actionInputValidator.validate(input);
        Conversation conversation = this.conversationRepository.getByIdOrHash(conversationIdOrHash);

        if (conversation == null) {
            throw new ConversationNotFoundException(conversationIdOrHash);
        }

        MessageAction action = this.modelMapper.map(input, MessageAction.class);
        this.conversationRepository.insertActionToMessage(conversation.getId(), messageId, action);

        return this.modelMapper.map(action, MessageActionOutput.class);
    }
}