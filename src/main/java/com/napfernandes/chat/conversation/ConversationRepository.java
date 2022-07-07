package com.napfernandes.chat.conversation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.napfernandes.chat.conversation.entity.Conversation;

public interface ConversationRepository extends MongoRepository<Conversation, String>, ConversationRepositoryCustom {
    @Query("{ '$or': [{'_id': ?0}, {'hash': ?0}] }")
    Conversation getByIdOrHash(String idOrHash);
}
