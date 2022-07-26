package com.napfernandes.chat.conversation;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.napfernandes.chat.conversation.entity.Conversation;
import com.napfernandes.chat.conversation.entity.ConversationMessage;
import com.napfernandes.chat.conversation.entity.MessageAction;

public class ConversationRepositoryImpl implements ConversationRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ConversationMessage insertMessageToConversation(String conversationId,
            ConversationMessage message) {

        message.setCreatedAt(LocalDateTime.now());
        message.setId(new ObjectId().toHexString());

        Criteria criteria = Criteria.where("_id").is(conversationId);
        Query query = new Query(criteria);
        Update update = new Update();
        update.push("messages", message);

        this.mongoTemplate.findAndModify(query, update, Conversation.class);

        return message;
    }

    @Override
    public MessageAction insertActionToMessage(String conversationId, String messageId, MessageAction action) {

        action.setCreatedAt(LocalDateTime.now());

        Criteria criteria = Criteria.where("_id").is(conversationId);
        Query query = new Query(criteria);
        Update update = new Update();
        update.push("messages.$[message].actions", action);
        update.filterArray("message.id", messageId);

        this.mongoTemplate.findAndModify(query, update, Conversation.class);

        return action;
    }
}
