package com.napfernandes.chat.conversation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.napfernandes.chat.cache.CacheService;
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

    private final ModelMapper modelMapper;
    private final CacheService cacheService;
    private final CryptoService cryptoService;
    private final ConversationRepository conversationRepository;
    private final ValidatorService<MessageActionInput> actionInputValidator;
    private final ValidatorService<ConversationInput> conversationInputValidator;
    private final ValidatorService<ConversationMessageInput> messageInputValidator;

    public ConversationServiceImpl(
            ModelMapper modelMapper,
            CacheService cacheService,
            CryptoService cryptoService,
            ConversationRepository conversationRepository,
            ValidatorService<MessageActionInput> actionInputValidator,
            ValidatorService<ConversationInput> conversationInputValidator,
            ValidatorService<ConversationMessageInput> messageInputValidator) {
        this.modelMapper = modelMapper;
        this.cacheService = cacheService;
        this.cryptoService = cryptoService;
        this.actionInputValidator = actionInputValidator;
        this.messageInputValidator = messageInputValidator;
        this.conversationRepository = conversationRepository;
        this.conversationInputValidator = conversationInputValidator;
    }

    @Override
    public List<ConversationOutput> findAllConversations() {
        String cacheKey = "findAllConversations";

        List<ConversationOutput> conversations = this.cacheService.getItemAsList(cacheKey, ConversationOutput.class);
        if (conversations != null) {
            return conversations;
        }

        conversations = this.conversationRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, ConversationOutput.class))
                .collect(Collectors.toList());
        this.cacheService.setItem(cacheKey, conversations);

        return conversations;
    }

    @Override
    public ConversationOutput getConversationByIdOrHash(String conversationIdOrHash)
            throws ConversationNotFoundException {
        String cacheKey = String.format("getConversationByIdOrHash#%s", conversationIdOrHash);
        ConversationOutput conversationOutput = this.cacheService.getItem(cacheKey, ConversationOutput.class);

        if (conversationOutput != null) {
            return conversationOutput;
        }

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
        String formattedString = cryptoService.hashValue(String.join(",", conversation.getMembers()));
        String conversationHash = this.cryptoService.hashValue(formattedString, null);

        conversation.setHash(conversationHash);
        conversation.setCreatedAt(LocalDateTime.now());

        this.conversationRepository.insert(conversation);

        return this.modelMapper.map(conversation, ConversationOutput.class);
    }

    @Override
    public ConversationMessageOutput insertMessageToConversation(String conversationIdOrHash,
            ConversationMessageInput input) throws ConversationNotFoundException {
        this.messageInputValidator.validate(input);

        ConversationMessage message = this.modelMapper.map(input, ConversationMessage.class);
        MessageAction messageAction = new MessageAction(input.getUserId(), MessageActionType.MessageSent,
                LocalDateTime.now());

        message.addAction(messageAction);

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